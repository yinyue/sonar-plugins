/*
 * Sonar Ant Task
 * Copyright (C) 2009 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */

package org.sonar.ant;

import org.junit.Test;
import org.sonar.wsclient.services.Violation;
import org.sonar.wsclient.services.ViolationQuery;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class JavaVersionIT extends AbstractIT {

  @Override
  protected String getProjectKey() {
    return "org.sonar.ant.tests:java-version";
  }

  @Override
  protected String getProfile() {
    return "java-version";
  }

  @Test
  public void projectMetrics() {
    ViolationQuery query = ViolationQuery.createForResource(getProjectKey()).setDepth(-1);
    List<Violation> violations = sonar.findAll(query);
    assertThat(violations.size(), is(1));
    assertThat(violations.get(0).getRuleKey(), is("pmd:IntegerInstantiation"));
  }
}
