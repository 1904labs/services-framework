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
import java.util.ArrayList;
import java.util.List;

/**
 * DocumentedTypeDTO
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-10-14T11:52:34.905+11:00")
public class DocumentedTypeDTO {
  @SerializedName("type")
  private String type = null;

  @SerializedName("description")
  private String description = null;

  @SerializedName("tags")
  private List<String> tags = new ArrayList<String>();

  public DocumentedTypeDTO type(String type) {
    this.type = type;
    return this;
  }

   /**
   * The fulley qualified name of the type.
   * @return type
  **/
  @ApiModelProperty(example = "null", value = "The fulley qualified name of the type.")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public DocumentedTypeDTO description(String description) {
    this.description = description;
    return this;
  }

   /**
   * The description of the type.
   * @return description
  **/
  @ApiModelProperty(example = "null", value = "The description of the type.")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public DocumentedTypeDTO tags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  public DocumentedTypeDTO addTagsItem(String tagsItem) {
    this.tags.add(tagsItem);
    return this;
  }

   /**
   * The tags associated with this type.
   * @return tags
  **/
  @ApiModelProperty(example = "null", value = "The tags associated with this type.")
  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocumentedTypeDTO documentedTypeDTO = (DocumentedTypeDTO) o;
    return Objects.equals(this.type, documentedTypeDTO.type) &&
        Objects.equals(this.description, documentedTypeDTO.description) &&
        Objects.equals(this.tags, documentedTypeDTO.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, description, tags);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentedTypeDTO {\n");
    
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
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

