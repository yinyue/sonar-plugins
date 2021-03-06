/*
 * Sonar Flex Plugin
 * Copyright (C) 2010 SonarSource
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

package org.sonar.plugins.flex.flexpmd;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.utils.ValidationMessages;

import java.io.Reader;

public class FlexPmdProfileExporterTest {

  private FlexPmdProfileExporter exporter;

  @Before
  public void setUp() {
    exporter = new FlexPmdProfileExporter();
  }

  @Test
  public void shouldSetMimeType() {
    assertThat(exporter.getMimeType(), is("application/xml"));
  }

  @Test
  public void shouldExport() {
    FlexPmdProfileImporter importer = new FlexPmdProfileImporter();
    Reader reader = FlexPmdProfileImporterTest.createProfileReader("flexpmd-simple-profile-with-fake.xml");
    RulesProfile profile = importer.importProfile(reader, ValidationMessages.create());

    String output = exporter.exportProfileToString(profile);

    assertThat(output, containsString("com.adobe.ac.pmd.rules.cairngorm.FatControllerRule"));
  }
}
