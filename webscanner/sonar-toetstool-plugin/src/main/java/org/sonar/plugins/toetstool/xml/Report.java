/*
 * Sonar Toetstool Plugin
 * Copyright (C) 2010 Matthijs Galesloot
 * dev@sonar.codehaus.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sonar.plugins.toetstool.xml;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * @author Matthijs Galesloot
 * @since 0.1
 */
public class Report {

  @XStreamAlias("guideline")
  public static class Guidelines {

    @XStreamImplicit(itemFieldName = "guideline")
    private List<Guideline> guidelines;

    public void setGuidelines(List<Guideline> guidelines) {
      this.guidelines = guidelines;
    }

    public List<Guideline> getGuidelines() {
      return guidelines;
    }
  }

  @XStreamAsAttribute
  private Counters counters;

  @XStreamAsAttribute
  private String date;


  @XStreamAsAttribute
  private Guidelines guidelines;

  @XStreamAsAttribute
  private Integer score;

  @XStreamAsAttribute
  private String url;

  public Counters getCounters() {
    return counters;
  }

  public String getDate() {
    return date;
  }

  public List<Guideline> getGuidelines() {
    return guidelines.getGuidelines();
  }

  public Integer getScore() {
    return score;
  }

  public String getUrl() {
    return url;
  }

  public void setCounters(Counters counters) {
    this.counters = counters;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setGuidelines(Guidelines guidelines) {
    this.guidelines = guidelines;
  }

  public void setScore(Integer score) {
    this.score = score;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}