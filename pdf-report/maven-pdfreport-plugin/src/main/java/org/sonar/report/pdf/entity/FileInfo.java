/*
 * Sonar PDF Report (Maven plugin)
 * Copyright (C) 2010 klicap - ingenieria del puzle
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

package org.sonar.report.pdf.entity;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;

public class FileInfo {

  /**
   * Sonar resource key.
   */
  private String key;

  /**
   * Resource name (filename).
   */
  private String name;

  /**
   * Number of violations ins this resource (file).
   */
  private String violations;

  /**
   * Class complexity.
   */
  private String complexity;

  /**
   * Duplicated lines in this resource (file)
   */
  private String duplicatedLines;

  /**
   * XPATH
   */
  private static final String ALL_FILES = "/resources/resource";
  private static final String KEY = "key";
  private static final String NAME = "name";
  private static final String VIOLATIONS_NUMBER = "msr/frmt_val";
  private static final String CCN = "msr/frmt_val";
  private static final String DUPLICATED_LINES = "msr/frmt_val";

  /**
   * It defines the content of this object: used for violations info, complexity info or duplications info.
   */
  public static final int VIOLATIONS_CONTENT = 1;
  public static final int CCN_CONTENT = 2;
  public static final int DUPLICATIONS_CONTENT = 3;

  /**
   * A FileInfo object could contain information about violations, ccn or duplications, this cases are distinguished in
   * function of content param, and defined by project context.
   * 
   * @param fileNode DOM Node that contains file info
   * @param content Type of content
   */
  public void initFromNode(Node fileNode, int content) {
    this.setKey(fileNode.selectSingleNode(KEY).getText());
    this.setName(fileNode.selectSingleNode(NAME).getText());

    if (content == VIOLATIONS_CONTENT) {
      this.setViolations(fileNode.selectSingleNode(VIOLATIONS_NUMBER).getText());
    } else if (content == CCN_CONTENT) {
      this.setComplexity(fileNode.selectSingleNode(CCN).getText());
    } else if (content == DUPLICATIONS_CONTENT) {
      this.setDuplicatedLines(fileNode.selectSingleNode(DUPLICATED_LINES).getText());
    }
  }

  public static List<FileInfo> initFromDocument(Document filesDocument, int content) {
    List<Node> fileNodes = filesDocument.selectNodes(ALL_FILES);
    List<FileInfo> fileInfoList = new LinkedList<FileInfo>();
    if (fileNodes != null) {
      Iterator<Node> it = fileNodes.iterator();
      while (it.hasNext()) {
        FileInfo file = new FileInfo();
        Node fileNode = it.next();
        // This first condition is a workarround for SONAR-830
        if (fileNode.selectSingleNode("msr") != null) {
          file.initFromNode(fileNode, content);
          if (file.isContentSet(content)) {
            fileInfoList.add(file);
          }
        }
      }
    }
    return fileInfoList;
  }

  public boolean isContentSet(int content) {
    boolean result = false;
    if (content == VIOLATIONS_CONTENT) {
      result = !this.getViolations().equals("0");
    } else if (content == CCN_CONTENT) {
      result = !this.getComplexity().equals("0");
    } else if (content == DUPLICATIONS_CONTENT) {
      result = !this.getDuplicatedLines().equals("0");
    }
    return result;
  }

  public String getKey() {
    return key;
  }

  public String getViolations() {
    return violations;
  }

  public String getComplexity() {
    return complexity;
  }

  public String getName() {
    return name;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setViolations(String violations) {
    this.violations = violations;
  }

  public void setComplexity(String complexity) {
    this.complexity = complexity;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDuplicatedLines() {
    return duplicatedLines;
  }

  public void setDuplicatedLines(String duplicatedLines) {
    this.duplicatedLines = duplicatedLines;
  }

}
