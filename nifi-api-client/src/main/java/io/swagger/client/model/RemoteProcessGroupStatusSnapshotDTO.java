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
 * RemoteProcessGroupStatusSnapshotDTO
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-10-14T11:52:34.905+11:00")
public class RemoteProcessGroupStatusSnapshotDTO {
  @SerializedName("id")
  private String id = null;

  @SerializedName("groupId")
  private String groupId = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("targetUri")
  private String targetUri = null;

  @SerializedName("transmissionStatus")
  private String transmissionStatus = null;

  @SerializedName("activeThreadCount")
  private Integer activeThreadCount = null;

  @SerializedName("flowFilesSent")
  private Integer flowFilesSent = null;

  @SerializedName("bytesSent")
  private Long bytesSent = null;

  @SerializedName("sent")
  private String sent = null;

  @SerializedName("flowFilesReceived")
  private Integer flowFilesReceived = null;

  @SerializedName("bytesReceived")
  private Long bytesReceived = null;

  @SerializedName("received")
  private String received = null;

  public RemoteProcessGroupStatusSnapshotDTO id(String id) {
    this.id = id;
    return this;
  }

   /**
   * The id of the remote process group.
   * @return id
  **/
  @ApiModelProperty(example = "null", value = "The id of the remote process group.")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public RemoteProcessGroupStatusSnapshotDTO groupId(String groupId) {
    this.groupId = groupId;
    return this;
  }

   /**
   * The id of the parent process group the remote process group resides in.
   * @return groupId
  **/
  @ApiModelProperty(example = "null", value = "The id of the parent process group the remote process group resides in.")
  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public RemoteProcessGroupStatusSnapshotDTO name(String name) {
    this.name = name;
    return this;
  }

   /**
   * The name of the remote process group.
   * @return name
  **/
  @ApiModelProperty(example = "null", value = "The name of the remote process group.")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RemoteProcessGroupStatusSnapshotDTO targetUri(String targetUri) {
    this.targetUri = targetUri;
    return this;
  }

   /**
   * The URI of the target system.
   * @return targetUri
  **/
  @ApiModelProperty(example = "null", value = "The URI of the target system.")
  public String getTargetUri() {
    return targetUri;
  }

  public void setTargetUri(String targetUri) {
    this.targetUri = targetUri;
  }

  public RemoteProcessGroupStatusSnapshotDTO transmissionStatus(String transmissionStatus) {
    this.transmissionStatus = transmissionStatus;
    return this;
  }

   /**
   * The transmission status of the remote process group.
   * @return transmissionStatus
  **/
  @ApiModelProperty(example = "null", value = "The transmission status of the remote process group.")
  public String getTransmissionStatus() {
    return transmissionStatus;
  }

  public void setTransmissionStatus(String transmissionStatus) {
    this.transmissionStatus = transmissionStatus;
  }

  public RemoteProcessGroupStatusSnapshotDTO activeThreadCount(Integer activeThreadCount) {
    this.activeThreadCount = activeThreadCount;
    return this;
  }

   /**
   * The number of active threads for the remote process group.
   * @return activeThreadCount
  **/
  @ApiModelProperty(example = "null", value = "The number of active threads for the remote process group.")
  public Integer getActiveThreadCount() {
    return activeThreadCount;
  }

  public void setActiveThreadCount(Integer activeThreadCount) {
    this.activeThreadCount = activeThreadCount;
  }

  public RemoteProcessGroupStatusSnapshotDTO flowFilesSent(Integer flowFilesSent) {
    this.flowFilesSent = flowFilesSent;
    return this;
  }

   /**
   * The number of FlowFiles sent to the remote process group in the last 5 minutes.
   * @return flowFilesSent
  **/
  @ApiModelProperty(example = "null", value = "The number of FlowFiles sent to the remote process group in the last 5 minutes.")
  public Integer getFlowFilesSent() {
    return flowFilesSent;
  }

  public void setFlowFilesSent(Integer flowFilesSent) {
    this.flowFilesSent = flowFilesSent;
  }

  public RemoteProcessGroupStatusSnapshotDTO bytesSent(Long bytesSent) {
    this.bytesSent = bytesSent;
    return this;
  }

   /**
   * The size of the FlowFiles sent to the remote process group in the last 5 minutes.
   * @return bytesSent
  **/
  @ApiModelProperty(example = "null", value = "The size of the FlowFiles sent to the remote process group in the last 5 minutes.")
  public Long getBytesSent() {
    return bytesSent;
  }

  public void setBytesSent(Long bytesSent) {
    this.bytesSent = bytesSent;
  }

  public RemoteProcessGroupStatusSnapshotDTO sent(String sent) {
    this.sent = sent;
    return this;
  }

   /**
   * The count/size of the flowfiles sent to the remote process group in the last 5 minutes.
   * @return sent
  **/
  @ApiModelProperty(example = "null", value = "The count/size of the flowfiles sent to the remote process group in the last 5 minutes.")
  public String getSent() {
    return sent;
  }

  public void setSent(String sent) {
    this.sent = sent;
  }

  public RemoteProcessGroupStatusSnapshotDTO flowFilesReceived(Integer flowFilesReceived) {
    this.flowFilesReceived = flowFilesReceived;
    return this;
  }

   /**
   * The number of FlowFiles received from the remote process group in the last 5 minutes.
   * @return flowFilesReceived
  **/
  @ApiModelProperty(example = "null", value = "The number of FlowFiles received from the remote process group in the last 5 minutes.")
  public Integer getFlowFilesReceived() {
    return flowFilesReceived;
  }

  public void setFlowFilesReceived(Integer flowFilesReceived) {
    this.flowFilesReceived = flowFilesReceived;
  }

  public RemoteProcessGroupStatusSnapshotDTO bytesReceived(Long bytesReceived) {
    this.bytesReceived = bytesReceived;
    return this;
  }

   /**
   * The size of the FlowFiles received from the remote process group in the last 5 minutes.
   * @return bytesReceived
  **/
  @ApiModelProperty(example = "null", value = "The size of the FlowFiles received from the remote process group in the last 5 minutes.")
  public Long getBytesReceived() {
    return bytesReceived;
  }

  public void setBytesReceived(Long bytesReceived) {
    this.bytesReceived = bytesReceived;
  }

  public RemoteProcessGroupStatusSnapshotDTO received(String received) {
    this.received = received;
    return this;
  }

   /**
   * The count/size of the flowfiles received from the remote process group in the last 5 minutes.
   * @return received
  **/
  @ApiModelProperty(example = "null", value = "The count/size of the flowfiles received from the remote process group in the last 5 minutes.")
  public String getReceived() {
    return received;
  }

  public void setReceived(String received) {
    this.received = received;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RemoteProcessGroupStatusSnapshotDTO remoteProcessGroupStatusSnapshotDTO = (RemoteProcessGroupStatusSnapshotDTO) o;
    return Objects.equals(this.id, remoteProcessGroupStatusSnapshotDTO.id) &&
        Objects.equals(this.groupId, remoteProcessGroupStatusSnapshotDTO.groupId) &&
        Objects.equals(this.name, remoteProcessGroupStatusSnapshotDTO.name) &&
        Objects.equals(this.targetUri, remoteProcessGroupStatusSnapshotDTO.targetUri) &&
        Objects.equals(this.transmissionStatus, remoteProcessGroupStatusSnapshotDTO.transmissionStatus) &&
        Objects.equals(this.activeThreadCount, remoteProcessGroupStatusSnapshotDTO.activeThreadCount) &&
        Objects.equals(this.flowFilesSent, remoteProcessGroupStatusSnapshotDTO.flowFilesSent) &&
        Objects.equals(this.bytesSent, remoteProcessGroupStatusSnapshotDTO.bytesSent) &&
        Objects.equals(this.sent, remoteProcessGroupStatusSnapshotDTO.sent) &&
        Objects.equals(this.flowFilesReceived, remoteProcessGroupStatusSnapshotDTO.flowFilesReceived) &&
        Objects.equals(this.bytesReceived, remoteProcessGroupStatusSnapshotDTO.bytesReceived) &&
        Objects.equals(this.received, remoteProcessGroupStatusSnapshotDTO.received);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, groupId, name, targetUri, transmissionStatus, activeThreadCount, flowFilesSent, bytesSent, sent, flowFilesReceived, bytesReceived, received);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RemoteProcessGroupStatusSnapshotDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    groupId: ").append(toIndentedString(groupId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    targetUri: ").append(toIndentedString(targetUri)).append("\n");
    sb.append("    transmissionStatus: ").append(toIndentedString(transmissionStatus)).append("\n");
    sb.append("    activeThreadCount: ").append(toIndentedString(activeThreadCount)).append("\n");
    sb.append("    flowFilesSent: ").append(toIndentedString(flowFilesSent)).append("\n");
    sb.append("    bytesSent: ").append(toIndentedString(bytesSent)).append("\n");
    sb.append("    sent: ").append(toIndentedString(sent)).append("\n");
    sb.append("    flowFilesReceived: ").append(toIndentedString(flowFilesReceived)).append("\n");
    sb.append("    bytesReceived: ").append(toIndentedString(bytesReceived)).append("\n");
    sb.append("    received: ").append(toIndentedString(received)).append("\n");
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

