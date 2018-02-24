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
import io.swagger.client.model.BulletinEntity;
import io.swagger.client.model.PermissionsDTO;
import io.swagger.client.model.PositionDTO;
import io.swagger.client.model.ProcessGroupDTO;
import io.swagger.client.model.ProcessGroupStatusDTO;
import io.swagger.client.model.RevisionDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * ProcessGroupEntity
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-10-14T11:52:34.905+11:00")
public class ProcessGroupEntity {
  @SerializedName("revision")
  private RevisionDTO revision = null;

  @SerializedName("id")
  private String id = null;

  @SerializedName("uri")
  private String uri = null;

  @SerializedName("position")
  private PositionDTO position = null;

  @SerializedName("permissions")
  private PermissionsDTO permissions = null;

  @SerializedName("bulletins")
  private List<BulletinEntity> bulletins = new ArrayList<BulletinEntity>();

  @SerializedName("component")
  private ProcessGroupDTO component = null;

  @SerializedName("status")
  private ProcessGroupStatusDTO status = null;

  @SerializedName("runningCount")
  private Integer runningCount = null;

  @SerializedName("stoppedCount")
  private Integer stoppedCount = null;

  @SerializedName("invalidCount")
  private Integer invalidCount = null;

  @SerializedName("disabledCount")
  private Integer disabledCount = null;

  @SerializedName("activeRemotePortCount")
  private Integer activeRemotePortCount = null;

  @SerializedName("inactiveRemotePortCount")
  private Integer inactiveRemotePortCount = null;

  @SerializedName("inputPortCount")
  private Integer inputPortCount = null;

  @SerializedName("outputPortCount")
  private Integer outputPortCount = null;

  public ProcessGroupEntity revision(RevisionDTO revision) {
    this.revision = revision;
    return this;
  }

   /**
   * The revision for this request/response. The revision is required for any mutable flow requests and is included in all responses.
   * @return revision
  **/
  @ApiModelProperty(example = "null", value = "The revision for this request/response. The revision is required for any mutable flow requests and is included in all responses.")
  public RevisionDTO getRevision() {
    return revision;
  }

  public void setRevision(RevisionDTO revision) {
    this.revision = revision;
  }

  public ProcessGroupEntity id(String id) {
    this.id = id;
    return this;
  }

   /**
   * The id of the component.
   * @return id
  **/
  @ApiModelProperty(example = "null", value = "The id of the component.")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ProcessGroupEntity uri(String uri) {
    this.uri = uri;
    return this;
  }

   /**
   * The URI for futures requests to the component.
   * @return uri
  **/
  @ApiModelProperty(example = "null", value = "The URI for futures requests to the component.")
  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public ProcessGroupEntity position(PositionDTO position) {
    this.position = position;
    return this;
  }

   /**
   * The position of this component in the UI if applicable.
   * @return position
  **/
  @ApiModelProperty(example = "null", value = "The position of this component in the UI if applicable.")
  public PositionDTO getPosition() {
    return position;
  }

  public void setPosition(PositionDTO position) {
    this.position = position;
  }

  public ProcessGroupEntity permissions(PermissionsDTO permissions) {
    this.permissions = permissions;
    return this;
  }

   /**
   * The permissions for this component.
   * @return permissions
  **/
  @ApiModelProperty(example = "null", value = "The permissions for this component.")
  public PermissionsDTO getPermissions() {
    return permissions;
  }

  public void setPermissions(PermissionsDTO permissions) {
    this.permissions = permissions;
  }

  public ProcessGroupEntity bulletins(List<BulletinEntity> bulletins) {
    this.bulletins = bulletins;
    return this;
  }

  public ProcessGroupEntity addBulletinsItem(BulletinEntity bulletinsItem) {
    this.bulletins.add(bulletinsItem);
    return this;
  }

   /**
   * The bulletins for this component.
   * @return bulletins
  **/
  @ApiModelProperty(example = "null", value = "The bulletins for this component.")
  public List<BulletinEntity> getBulletins() {
    return bulletins;
  }

  public void setBulletins(List<BulletinEntity> bulletins) {
    this.bulletins = bulletins;
  }

  public ProcessGroupEntity component(ProcessGroupDTO component) {
    this.component = component;
    return this;
  }

   /**
   * Get component
   * @return component
  **/
  @ApiModelProperty(example = "null", value = "")
  public ProcessGroupDTO getComponent() {
    return component;
  }

  public void setComponent(ProcessGroupDTO component) {
    this.component = component;
  }

  public ProcessGroupEntity status(ProcessGroupStatusDTO status) {
    this.status = status;
    return this;
  }

   /**
   * The status of the process group.
   * @return status
  **/
  @ApiModelProperty(example = "null", value = "The status of the process group.")
  public ProcessGroupStatusDTO getStatus() {
    return status;
  }

  public void setStatus(ProcessGroupStatusDTO status) {
    this.status = status;
  }

  public ProcessGroupEntity runningCount(Integer runningCount) {
    this.runningCount = runningCount;
    return this;
  }

   /**
   * The number of running componetns in this process group.
   * @return runningCount
  **/
  @ApiModelProperty(example = "null", value = "The number of running componetns in this process group.")
  public Integer getRunningCount() {
    return runningCount;
  }

  public void setRunningCount(Integer runningCount) {
    this.runningCount = runningCount;
  }

  public ProcessGroupEntity stoppedCount(Integer stoppedCount) {
    this.stoppedCount = stoppedCount;
    return this;
  }

   /**
   * The number of stopped components in the process group.
   * @return stoppedCount
  **/
  @ApiModelProperty(example = "null", value = "The number of stopped components in the process group.")
  public Integer getStoppedCount() {
    return stoppedCount;
  }

  public void setStoppedCount(Integer stoppedCount) {
    this.stoppedCount = stoppedCount;
  }

  public ProcessGroupEntity invalidCount(Integer invalidCount) {
    this.invalidCount = invalidCount;
    return this;
  }

   /**
   * The number of invalid components in the process group.
   * @return invalidCount
  **/
  @ApiModelProperty(example = "null", value = "The number of invalid components in the process group.")
  public Integer getInvalidCount() {
    return invalidCount;
  }

  public void setInvalidCount(Integer invalidCount) {
    this.invalidCount = invalidCount;
  }

  public ProcessGroupEntity disabledCount(Integer disabledCount) {
    this.disabledCount = disabledCount;
    return this;
  }

   /**
   * The number of disabled components in the process group.
   * @return disabledCount
  **/
  @ApiModelProperty(example = "null", value = "The number of disabled components in the process group.")
  public Integer getDisabledCount() {
    return disabledCount;
  }

  public void setDisabledCount(Integer disabledCount) {
    this.disabledCount = disabledCount;
  }

  public ProcessGroupEntity activeRemotePortCount(Integer activeRemotePortCount) {
    this.activeRemotePortCount = activeRemotePortCount;
    return this;
  }

   /**
   * The number of active remote ports in the process group.
   * @return activeRemotePortCount
  **/
  @ApiModelProperty(example = "null", value = "The number of active remote ports in the process group.")
  public Integer getActiveRemotePortCount() {
    return activeRemotePortCount;
  }

  public void setActiveRemotePortCount(Integer activeRemotePortCount) {
    this.activeRemotePortCount = activeRemotePortCount;
  }

  public ProcessGroupEntity inactiveRemotePortCount(Integer inactiveRemotePortCount) {
    this.inactiveRemotePortCount = inactiveRemotePortCount;
    return this;
  }

   /**
   * The number of inactive remote ports in the process group.
   * @return inactiveRemotePortCount
  **/
  @ApiModelProperty(example = "null", value = "The number of inactive remote ports in the process group.")
  public Integer getInactiveRemotePortCount() {
    return inactiveRemotePortCount;
  }

  public void setInactiveRemotePortCount(Integer inactiveRemotePortCount) {
    this.inactiveRemotePortCount = inactiveRemotePortCount;
  }

  public ProcessGroupEntity inputPortCount(Integer inputPortCount) {
    this.inputPortCount = inputPortCount;
    return this;
  }

   /**
   * The number of input ports in the process group.
   * @return inputPortCount
  **/
  @ApiModelProperty(example = "null", value = "The number of input ports in the process group.")
  public Integer getInputPortCount() {
    return inputPortCount;
  }

  public void setInputPortCount(Integer inputPortCount) {
    this.inputPortCount = inputPortCount;
  }

  public ProcessGroupEntity outputPortCount(Integer outputPortCount) {
    this.outputPortCount = outputPortCount;
    return this;
  }

   /**
   * The number of output ports in the process group.
   * @return outputPortCount
  **/
  @ApiModelProperty(example = "null", value = "The number of output ports in the process group.")
  public Integer getOutputPortCount() {
    return outputPortCount;
  }

  public void setOutputPortCount(Integer outputPortCount) {
    this.outputPortCount = outputPortCount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProcessGroupEntity processGroupEntity = (ProcessGroupEntity) o;
    return Objects.equals(this.revision, processGroupEntity.revision) &&
        Objects.equals(this.id, processGroupEntity.id) &&
        Objects.equals(this.uri, processGroupEntity.uri) &&
        Objects.equals(this.position, processGroupEntity.position) &&
        Objects.equals(this.permissions, processGroupEntity.permissions) &&
        Objects.equals(this.bulletins, processGroupEntity.bulletins) &&
        Objects.equals(this.component, processGroupEntity.component) &&
        Objects.equals(this.status, processGroupEntity.status) &&
        Objects.equals(this.runningCount, processGroupEntity.runningCount) &&
        Objects.equals(this.stoppedCount, processGroupEntity.stoppedCount) &&
        Objects.equals(this.invalidCount, processGroupEntity.invalidCount) &&
        Objects.equals(this.disabledCount, processGroupEntity.disabledCount) &&
        Objects.equals(this.activeRemotePortCount, processGroupEntity.activeRemotePortCount) &&
        Objects.equals(this.inactiveRemotePortCount, processGroupEntity.inactiveRemotePortCount) &&
        Objects.equals(this.inputPortCount, processGroupEntity.inputPortCount) &&
        Objects.equals(this.outputPortCount, processGroupEntity.outputPortCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(revision, id, uri, position, permissions, bulletins, component, status, runningCount, stoppedCount, invalidCount, disabledCount, activeRemotePortCount, inactiveRemotePortCount, inputPortCount, outputPortCount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProcessGroupEntity {\n");
    
    sb.append("    revision: ").append(toIndentedString(revision)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    uri: ").append(toIndentedString(uri)).append("\n");
    sb.append("    position: ").append(toIndentedString(position)).append("\n");
    sb.append("    permissions: ").append(toIndentedString(permissions)).append("\n");
    sb.append("    bulletins: ").append(toIndentedString(bulletins)).append("\n");
    sb.append("    component: ").append(toIndentedString(component)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    runningCount: ").append(toIndentedString(runningCount)).append("\n");
    sb.append("    stoppedCount: ").append(toIndentedString(stoppedCount)).append("\n");
    sb.append("    invalidCount: ").append(toIndentedString(invalidCount)).append("\n");
    sb.append("    disabledCount: ").append(toIndentedString(disabledCount)).append("\n");
    sb.append("    activeRemotePortCount: ").append(toIndentedString(activeRemotePortCount)).append("\n");
    sb.append("    inactiveRemotePortCount: ").append(toIndentedString(inactiveRemotePortCount)).append("\n");
    sb.append("    inputPortCount: ").append(toIndentedString(inputPortCount)).append("\n");
    sb.append("    outputPortCount: ").append(toIndentedString(outputPortCount)).append("\n");
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
