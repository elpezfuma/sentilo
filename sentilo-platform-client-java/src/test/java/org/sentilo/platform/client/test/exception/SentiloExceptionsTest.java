/*
 * Sentilo
 * 
 * Copyright (C) 2013 Institut Municipal d’Informàtica, Ajuntament de Barcelona.
 * 
 * This program is licensed and may be used, modified and redistributed under the terms of the
 * European Public License (EUPL), either version 1.1 or (at your option) any later version as soon
 * as they are approved by the European Commission.
 * 
 * Alternatively, you may redistribute and/or modify this program under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 * 
 * See the licenses for the specific language governing permissions, limitations and more details.
 * 
 * You should have received a copy of the EUPL1.1 and the LGPLv3 licenses along with this program;
 * if not, you may find them at:
 * 
 * https://joinup.ec.europa.eu/software/page/eupl/licence-eupl http://www.gnu.org/licenses/ and
 * https://www.gnu.org/licenses/lgpl.txt
 */
package org.sentilo.platform.client.test.exception;

import org.junit.Assert;
import org.junit.Test;
import org.sentilo.platform.client.core.exception.PlatformClientAccessException;
import org.sentilo.platform.client.core.exception.PlatformClientExceptionTranslator;
import org.springframework.core.NestedExceptionUtils;

public class SentiloExceptionsTest {

  @Test
  public void platformClientAccessException() {

    final String message = "mock exception message";
    final Throwable cause = new Exception();

    final PlatformClientAccessException exception = new PlatformClientAccessException(message);
    Assert.assertNull(exception.getCause());
    Assert.assertEquals(message, exception.getMessage());

    final PlatformClientAccessException exception2 = new PlatformClientAccessException(message, cause);
    Assert.assertEquals(cause, exception2.getCause());
    Assert.assertEquals(NestedExceptionUtils.buildMessage(message, cause), exception2.getMessage());
  }

  @Test
  public void translatePlatformClientAccessException() {
    final String message = "mock exception message";
    final PlatformClientExceptionTranslator translator = new PlatformClientExceptionTranslator();

    final RuntimeException ex = new PlatformClientAccessException(message);
    try {
      translator.translateExceptionIfPossible(ex);
    } catch (final PlatformClientAccessException exTrans) {
      Assert.assertEquals(ex, exTrans);
    }
  }

  @Test
  public void translateNoPlatformClientAccessException() {
    final String message = "mock exception message";
    final PlatformClientExceptionTranslator translator = new PlatformClientExceptionTranslator();

    final RuntimeException ex = new NullPointerException(message);
    try {
      translator.translateExceptionIfPossible(ex);
    } catch (final PlatformClientAccessException exTrans) {
      Assert.assertNotEquals(ex, exTrans);
      Assert.assertTrue(exTrans instanceof PlatformClientAccessException);
      Assert.assertEquals(ex, exTrans.getCause());
    }
  }
}
