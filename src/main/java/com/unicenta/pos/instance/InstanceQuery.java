//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (c) 2009-2018 uniCenta & previous Openbravo POS works
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
//    along with uniCenta oPOS.  If not, see <http://www.gnu.org/licenses/>.

package com.unicenta.pos.instance;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * InstanceQuery is responsible for connecting to the remote RMI registry
 * and retrieving a reference to an AppMessage instance.
 */
public class InstanceQuery {
    private final AppMessage m_appstub;

    /**
     * Creates a new instance of InstanceQuery.
     *
     * @throws RemoteException if there is an issue with the remote method invocation
     * @throws NotBoundException if the specified name does not exist in the RMI registry
     */
    public InstanceQuery() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();
        m_appstub = (AppMessage) registry.lookup("AppMessage");
    }

    /**
     * Returns the retrieved AppMessage instance.
     *
     * @return the AppMessage instance
     */
    public AppMessage getAppMessage() {
        return m_appstub;
    }
}
