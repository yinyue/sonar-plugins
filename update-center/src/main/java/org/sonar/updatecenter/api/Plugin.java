package org.sonar.updatecenter.api;

import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Information about Sonar Plugin.
 *
 * @author Evgeny Mandrikov
 */
public class Plugin implements Versioned {
  private String pluginClass;
  private String name;
  private String version;
  private String downloadUrl;
  private String requiredSonarVersion;
  private String homepage;

  public Plugin(String pluginClass) {
    this.pluginClass = pluginClass;
  }

  public String getPluginClass() {
    return pluginClass;
  }

  /**
   * @return name
   */
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return version
   */
  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  /**
   * @return URL for downloading
   */
  public String getDownloadUrl() {
    return downloadUrl;
  }

  public void setDownloadUrl(String downloadUrl) {
    this.downloadUrl = downloadUrl;
  }

  /**
   * @return minimal Sonar version to run this plugin
   */
  public String getRequiredSonarVersion() {
    // TODO Sonar-Version from MANIFEST.MF
    return requiredSonarVersion;
  }

  public void setRequiredSonarVersion(String sonarVersion) {
    this.requiredSonarVersion = sonarVersion;
  }

  /**
   * @return homepage
   */
  public String getHomepage() {
    // TODO Plugin-Homepage from MANIFEST.MF
    return homepage;
  }

  public void setHomepage(String homepage) {
    this.homepage = homepage;
  }

  public JSONObject toJsonObject() {
    JSONObject obj = new JSONObject();
    obj.put("id", getPluginClass());
    obj.put("name", getName());
    obj.put("version", getVersion());
    obj.put("sonarVersion", getRequiredSonarVersion());
    if (getDownloadUrl() != null) {
      obj.put("downloadUrl", getDownloadUrl());
    }
    if (getHomepage() != null) {
      obj.put("homepage", getHomepage());
    }
    return obj;
  }

  public static Plugin extractMetadata(File file) throws IOException {
    JarFile jar = new JarFile(file);
    Manifest manifest = jar.getManifest();
    jar.close();

    Attributes attributes = manifest.getMainAttributes();
    String pluginClass = attributes.getValue("Plugin-Class");
    Plugin plugin = new Plugin(pluginClass);
    plugin.setName(attributes.getValue("Plugin-Name"));
    plugin.setVersion(attributes.getValue("Plugin-Version"));
    plugin.setRequiredSonarVersion(attributes.getValue("Sonar-Version"));
    plugin.setHomepage(attributes.getValue("Plugin-Homepage"));
    return plugin;
  }
}