package com.mokshesh.ontology.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@Getter
@Setter
public class ObjectType {
  private ObjectId id;
  private Integer version;
  private String collection;
  private String externalId;
  private String displayName;
  private String pluralName;
  private String description;
  private List<Property> properties;
  private List<Relation> relations;
  private int usageStatus;
  private Long modifiedAt;
  private User modifiedBy;
  private Long createdAt;
  private User createdBy;
}
