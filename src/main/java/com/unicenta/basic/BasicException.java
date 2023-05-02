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

package com.unicenta.basic;

/**
 * BasicException is a custom exception class for handling exceptions in the uniCenta oPOS application.
 *
 * @author JG uniCenta
 */
public class BasicException extends Exception {

    /**
     * Creates a new instance of <code>BasicException</code> without detailed message.
     */
    public BasicException() {
    }

    /**
     * Constructs a new <code>BasicException</code> with the specified detailed message.
     *
     * @param message the detail message.
     */
    public BasicException(String message) {
        super(message);
    }

    /**
     * Constructs a new <code>BasicException</code> with the specified detailed message and cause.
     *
     * @param message the detailed message.
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).
     *                A {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.
     */
    public BasicException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new <code>BasicException</code> with the specified cause and a detail message of
     * {@code (cause==null ? null : cause.toString())} (which typically contains the class and detail message of {@code cause}).
     * This constructor is useful for exceptions that are little more than wrappers for other throwables.
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
     *              A {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.
     */
    public BasicException(Throwable cause) {
        super(cause);
    }
}