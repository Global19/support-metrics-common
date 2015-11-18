/**
 * Copyright 2015 Confluent Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.confluent.support.metrics.submitters;

import org.apache.http.client.methods.HttpPost;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

public class ConfluentSubmitterTest {

  @Test
  public void testInvalidArgumentsForConstructorNullEndpoints() {
    // Given
    String httpEndpoint = null;
    String httpsEndpoint = null;

    // When/Then
    try {
      new ConfluentSubmitter(httpEndpoint, httpsEndpoint);
      fail("IllegalArgumentException expected because endpoints are null");
    } catch (IllegalArgumentException e) {
      assertThat(e).hasMessage("must specify Confluent Service endpoint");
    }
  }

  @Test
  public void testInvalidArgumentsForConstructorEmptyEndpoints() {
    // Given
    String httpEndpoint = "";
    String httpsEndpoint = "";

    // When/Then
    try {
      new ConfluentSubmitter(httpEndpoint, httpsEndpoint);
      fail("IllegalArgumentException expected because endpoints are empty");
    } catch (IllegalArgumentException e) {
      assertThat(e).hasMessage("must specify Confluent Service endpoint");
    }
  }

  @Test
  public void testInvalidArgumentsForConstructorNullEmptyEndpoints() {
    // Given
    String httpEndpoint = null;
    String httpsEndpoint = "";

    // When/Then
    try {
      new ConfluentSubmitter(httpEndpoint, httpsEndpoint);
      fail("IllegalArgumentException expected because endpoints are empty");
    } catch (IllegalArgumentException e) {
      assertThat(e).hasMessage("must specify Confluent Service endpoint");
    }
  }

  @Test
  public void testInvalidArgumentsForConstructorEmptyNullEndpoints() {
    // Given
    String httpEndpoint = "";
    String httpsEndpoint = null;

    // When/Then
    try {
      new ConfluentSubmitter(httpEndpoint, httpsEndpoint);
      fail("IllegalArgumentException expected because endpoints are empty");
    } catch (IllegalArgumentException e) {
      assertThat(e).hasMessage("must specify Confluent Service endpoint");
    }
  }

  @Test
  public void testValidArgumentsForConstructor() {
    // Given
    String httpEndpoint = "http://example.com";
    String httpsEndpoint = "https://example.com";

    // When/Then
    new ConfluentSubmitter(httpEndpoint, httpsEndpoint);
  }

  @Test
  public void testInvalidArgumentsForConstructorInvalidEndpoints() {
    // Given
    String httpEndpoint = "not a valid URL";
    String httpsEndpoint = "https://not a valid URL";

    // When/Then
    try {
      new ConfluentSubmitter(httpEndpoint, httpsEndpoint);
      fail("IllegalArgumentException expected because endpoints are invalid");
    } catch (Exception e) {
      assertThat(e).hasMessageStartingWith("invalid Confluent Service HTTP");
    }
  }

  @Test
  public void testInvalidArgumentsForConstructorMismatchedEndpoints() {
    // Given
    String httpEndpoint = "https://example.com";
    String httpsEndpoint = "http://example.com";

    // When/Then
    try {
      new ConfluentSubmitter(httpEndpoint, httpsEndpoint);
      fail("IllegalArgumentException expected because endpoints are invalid");
    } catch (Exception e) {
      assertThat(e).hasMessageStartingWith("invalid Confluent Service HTTP");
    }
  }

  @Test
  public void testSubmitIgnoresNullInput() {
    // Given
    String httpEndpoint = "http://example.com";
    String httpsEndpoint = "https://example.com";
    HttpPost p = mock(HttpPost.class);
    ConfluentSubmitter c = new ConfluentSubmitter(httpEndpoint, httpsEndpoint);
    byte[] nullData = null;

    // When
    c.submit(nullData, p);

    // Then
    verifyZeroInteractions(p);
  }
}