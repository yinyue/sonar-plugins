/*
 * Sonar W3C Markup Validation Plugin
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
package org.sonar.plugins.web.markup.rules;

import java.util.List;

import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rules.Rule;
import org.sonar.api.rules.RulePriority;
import org.sonar.api.utils.ValidationMessages;
import org.sonar.plugins.web.api.WebConstants;

/**
 * Default rukles profile containing all markup rules.
 *
 * @author Matthijs Galesloot
 * @since 1.0
 */
public final class DefaultMarkupProfile extends ProfileDefinition {

  @Override
  public RulesProfile createProfile(ValidationMessages validation) {
    MarkupRuleRepository repository = new MarkupRuleRepository();
    List<Rule> rules = repository.createRules();
    RulesProfile rulesProfile = RulesProfile.create(MarkupRuleRepository.REPOSITORY_NAME, WebConstants.LANGUAGE_KEY);
    for (Rule rule : rules) {
      rulesProfile.activateRule(rule, RulePriority.MAJOR);
    }
    rulesProfile.setDefaultProfile(false);
    return rulesProfile;
  }
}
