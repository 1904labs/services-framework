/*
 * NiFi Rest Api
 * The Rest Api provides programmatic access to command and control a NiFi instance in real time. Start and                                              stop processors, monitor queues, query provenance data, and more. Each endpoint below includes a description,                                             definitions of the expected input and output, potential response codes, and the authorizations required                                             to invoke each service.
 *
 * OpenAPI spec version: 1.0.0
 * Contact: dev@nifi.apache.org
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
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


package io.swagger.client.model;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * AllowableValueDTO
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-10-14T11:52:34.905+11:00")
public class AllowableValueDTO {
  @SerializedName("displayName")
  private String displayName = null;

  @SerializedName("value")
  private String value = null;

  @SerializedName("description")
  private String description = null;

  public AllowableValueDTO displayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

   /**
   * A human readable value that is allowed for the property descriptor.
   * @return displayName
  **/
  @ApiModelProperty(example = "null", value = "A human readable value that is allowed for the property descriptor.")
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public AllowableValueDTO value(String value) {
    this.value = value;
    return this;
  }

   /**
   * A value that is allowed for the property descriptor.
   * @return value
  **/
  @ApiModelProperty(example = "null", value = "A value that is allowed for the property descriptor.")
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public AllowableValueDTO description(String description) {
    this.description = description;
    return this;
  }

   /**
   * A description for this allowable value.
   * @return description
  **/
  @ApiModelProperty(example = "null", value = "A description for this allowable value.")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AllowableValueDTO allowableValueDTO = (AllowableValueDTO) o;
    return Objects.equals(this.displayName, allowableValueDTO.displayName) &&
        Objects.equals(this.value, allowableValueDTO.value) &&
        Objects.equals(this.description, allowableValueDTO.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(displayName, value, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllowableValueDTO {\n");
    
    sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
}

