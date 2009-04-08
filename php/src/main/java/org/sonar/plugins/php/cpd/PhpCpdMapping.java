/*
 * Sonar, open source software quality management tool.
 * Copyright (C) 2009 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * Sonar is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */

package org.sonar.plugins.php.cpd;

import net.sourceforge.pmd.cpd.PHPTokenizer;
import net.sourceforge.pmd.cpd.Tokenizer;
import org.sonar.commons.Language;
import org.sonar.commons.resources.Resource;
import org.sonar.plugins.cpd.AbstractCpdMapping;
import org.sonar.plugins.php.Php;

import java.util.Arrays;

public class PhpCpdMapping extends AbstractCpdMapping {

  protected Tokenizer getTokenizer(){
    return new PHPTokenizer();
  }

  protected String[] getSuffixes() {
    return Php.SUFFIXES;
  }

  protected Resource createResource(String absolutePath, String sourceDir) {
    return Php.newFileFromAbsolutePath(absolutePath, Arrays.asList(sourceDir));
  }

  protected Language getLanguage() {
    return new Php();
  }
}
