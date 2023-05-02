//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (c) 2009-2018 uniCenta
//    https://unicenta.com
//
//    This file is part of uniCenta oPOS
//
//    uniCenta oPOS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//   uniCenta oPOS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with uniCenta oPOS.  If not, see <http://www.gnu.org/licenses/>

package com.unicenta.pos.forms;

import com.unicenta.pos.instance.InstanceQuery;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.SwingUtilities;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.util.Locale;
import com.unicenta.format.Formats;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Constructor;

@Slf4j
public class StartPOS {
    private StartPOS() {
    }

    public static boolean registerApp() {
        InstanceQuery instance = null;
        try {
            instance = new InstanceQuery();
            instance.getAppMessage().restoreWindow();
            return false;
        } catch (RemoteException | NotBoundException e) {
            return true;
        }
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            if (!registerApp()) {
                System.exit(1);
            }

            AppConfig config = new AppConfig(args);

            config.load();

            String language = config.getProperty("user.language");
            String country = config.getProperty("user.country");
            String variant = config.getProperty("user.variant");
            if (language != null && !language.isEmpty() && country != null && variant != null) {
                Locale.setDefault(new Locale(language, country, variant));
            }

            Formats.setIntegerPattern(config.getProperty("format.integer"));
            Formats.setDoublePattern(config.getProperty("format.double"));
            Formats.setCurrencyPattern(config.getProperty("format.currency"));
            Formats.setPercentPattern(config.getProperty("format.percent"));
            Formats.setDatePattern(config.getProperty("format.date"));
            Formats.setTimePattern(config.getProperty("format.time"));
            Formats.setDateTimePattern(config.getProperty("format.datetime"));

            // Set the look and feel
            try {
    Class<?> lafClass = Class.forName(config.getProperty("swing.defaultlaf"));
    Object laf;
    if (LookAndFeel.class.isAssignableFrom(lafClass)) {
        Constructor<?> constructor = lafClass.getDeclaredConstructor();
        laf = constructor.newInstance();
    } else {
        Constructor<?> constructor = lafClass.getDeclaredConstructor();
        laf = constructor.newInstance();
    }
    if (!(laf instanceof MetalLookAndFeel) && laf instanceof LookAndFeel) {
        UIManager.setLookAndFeel((LookAndFeel) laf);
    } else {
        UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatGrayIJTheme");
    }
} catch (Exception e) {
    log.error("Cannot set Look and Feel {0}", e.getMessage());
}

            String hostname = config.getProperty("machine.hostname");
            TicketInfo.setHostname(hostname);
            applicationStarted(hostname);

            String screenMode = config.getProperty("machine.screenmode");

            if ("fullscreen".equals(screenMode)) {
                JRootKiosk rootKiosk = new JRootKiosk();
                try {
                    rootKiosk.initFrame(config);
                } catch (IOException ex) {
                    log.error(ex.getMessage());
                }
            } else {
                JRootFrame rootFrame = new JRootFrame();
                try {
                    rootFrame.initFrame(config);
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                }
            }
        });
    }

    private static void applicationStarted(String host) {
        new Thread(() -> {
            Metrics metrics = new Metrics();
            metrics.setDevice(host);
            metrics.setUniCentaVersion(AppLocal.APP_VERSION);
            new Application().postMetrics(metrics);
        }).start();
    }
}