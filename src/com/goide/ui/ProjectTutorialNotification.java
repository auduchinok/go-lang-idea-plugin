/*
 * Copyright 2013-2015 Sergey Ignatov, Alexander Zolotov, Florin Patan
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

package com.goide.ui;

import com.goide.GoConstants;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.notification.NotificationListener;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.util.text.VersionComparatorUtil;
import org.jetbrains.annotations.NotNull;

import static com.goide.util.GoUtil.PLUGIN_VERSION;

public class ProjectTutorialNotification implements ApplicationComponent {

  private static final String GO_PROJECT_TUTORIAL_NOTIFICATION_SHOWN = "go.tutorial.project.notification.shown";

  @Override
  public void initComponent() {
    final PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
    boolean shownAlready;
    //noinspection SynchronizationOnLocalVariableOrMethodParameter
    synchronized (propertiesComponent) {
      String storedVersion = propertiesComponent.getValue(GO_PROJECT_TUTORIAL_NOTIFICATION_SHOWN, "0.0");
      shownAlready = VersionComparatorUtil.compare(storedVersion, PLUGIN_VERSION) == 0;
      propertiesComponent.setValue(GO_PROJECT_TUTORIAL_NOTIFICATION_SHOWN, PLUGIN_VERSION);
    }

    if (shownAlready) {
      return;
    }

    Notifications.Bus.notify(GoConstants.GO_NOTIFICATION_GROUP.createNotification("Learn how to setup a new Go project",
      "Please visit our " +
      "<a href=\"https://github.com/go-lang-plugin-org/go-lang-idea-plugin/wiki/v1.0.0-Setup-initial-project\">wiki page<a/>" +
      " to learn how to setup a new Go project",
      NotificationType.INFORMATION,
      NotificationListener.URL_OPENING_LISTENER));
  }

  @Override
  public void disposeComponent() {

  }

  @NotNull
  @Override
  public String getComponentName() {
    return getClass().getName();
  }
}