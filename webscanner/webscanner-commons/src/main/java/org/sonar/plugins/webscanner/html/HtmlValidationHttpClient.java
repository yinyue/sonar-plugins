/*
 * Copyright (C) 2010 Matthijs Galesloot
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

package org.sonar.plugins.webscanner.html;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.log4j.Logger;
import org.sonar.plugins.webscanner.ssl.EasySSLProtocolSocketFactory;

/**
 * Client
 *
 * @author A130564
 *
 */
public class HtmlValidationHttpClient {

  private static final Logger LOG = Logger.getLogger(HtmlValidationHttpClient.class);

  static {
    Protocol.registerProtocol("https", new Protocol("https", (ProtocolSocketFactory) new EasySSLProtocolSocketFactory(), 443));
  }

  private final HttpClient client;

  private String proxyHost;

  private int proxyPort;

  public HtmlValidationHttpClient() {
    this.client = new HttpClient();
    if (useProxy()) {
      client.getHostConfiguration().setProxy(getProxyHost(), getProxyPort());
    }
  }

  protected void executePostMethod(PostMethod post) {

    int retries = 3;

    for (int i = 0; i < retries; i++) {
      try {
        getClient().executeMethod(post);
        if (post.getStatusCode() == 200 || post.getStatusCode() == 302) {
          return;
        } else {
          LOG.warn("Bad http response: " + post.getStatusCode() + ", retrying after 1 second...");
          sleep(1000L);
        }
      } catch (UnknownHostException e) {
        if (useProxy()) {
          LOG.warn("Unknown host, retry without proxy...");
          getClient().getHostConfiguration().setProxyHost(null);
          try {
            getClient().executeMethod(post);
          } catch (IOException e2) {
            throw new RuntimeException(e2);
          }
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  protected HttpClient getClient() {
    return client;
  }

  /**
   * Returns the proxy host.
   *
   * @return proxy host
   */
  public String getProxyHost() {
    return proxyHost;
  }

  /**
   * Returns the proxy port.
   *
   * @return proxy port
   */
  public int getProxyPort() {
    return proxyPort;
  }

  public void setProxyHost(String proxyHost) {
    this.proxyHost = proxyHost;
  }

  public void setProxyPort(int proxyPort) {
    this.proxyPort = proxyPort;
  }

  protected void sleep(long sleepInterval) {
    try {
      Thread.sleep(sleepInterval);
    } catch (InterruptedException ie) {
      throw new RuntimeException(ie);
    }
  }

  /**
   * Returns whether or not to use a proxy.
   *
   * @return true/false
   */
  public boolean useProxy() {
    return proxyHost != null && proxyPort > 0;
  }

}