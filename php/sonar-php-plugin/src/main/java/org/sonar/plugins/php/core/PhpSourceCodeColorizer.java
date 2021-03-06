/*
 * Sonar PHP Plugin
 * Copyright (C) 2010 Sonar PHP Plugin
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

package org.sonar.plugins.php.core;

import static org.sonar.plugins.php.api.Php.PHP_KEYWORDS_ARRAY;
import static org.sonar.plugins.php.api.Php.PHP_RESERVED_VARIABLES_ARRAY;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sonar.api.web.CodeColorizerFormat;
import org.sonar.colorizer.CDocTokenizer;
import org.sonar.colorizer.CppDocTokenizer;
import org.sonar.colorizer.KeywordsTokenizer;
import org.sonar.colorizer.StringTokenizer;
import org.sonar.colorizer.Tokenizer;
import org.sonar.plugins.php.api.Php;

/**
 * @author freddy.mallet
 * 
 */
public class PhpSourceCodeColorizer extends CodeColorizerFormat {

  /**
   * 
   */
  private static final Set<String> PHP_KEYWORDS = new HashSet<String>();
  private static final Set<String> PHP_RESERVED_VARIABLES = new HashSet<String>();

  static {
    Collections.addAll(PHP_KEYWORDS, PHP_KEYWORDS_ARRAY);
    Collections.addAll(PHP_RESERVED_VARIABLES, PHP_RESERVED_VARIABLES_ARRAY);
  }

  /**
   * Simple constructor
   */
  public PhpSourceCodeColorizer() {
    super(Php.KEY);
  }

  /**
   * We use here the C/C++ tokenizers, the custom PHP Tokenizer and the standard String tokenir (handles simple quotes and double quotes
   * delimited strings).
   * 
   * @see org.sonar.api.web.CodeColorizerFormat#getTokenizers()
   */
  @Override
  public List<Tokenizer> getTokenizers() {
    String tagAfter = "</span>";
    KeywordsTokenizer phpKeyWordsTokenizer = new KeywordsTokenizer("<span class=\"k\">", tagAfter, PHP_KEYWORDS);
    KeywordsTokenizer phpVariablesTokenizer = new KeywordsTokenizer("<span class=\"c\">", tagAfter, PHP_RESERVED_VARIABLES);
    CppDocTokenizer cppDocTokenizer = new CppDocTokenizer("<span class=\"cppd\">", tagAfter);
    CDocTokenizer cDocTokenizer = new CDocTokenizer("<span class=\"cd\">", tagAfter);
    StringTokenizer stringTokenizer = new StringTokenizer("<span class=\"s\">", tagAfter);
    return Collections.unmodifiableList(Arrays.asList(cDocTokenizer, cppDocTokenizer, phpKeyWordsTokenizer, stringTokenizer,
        phpVariablesTokenizer));
  }
}
