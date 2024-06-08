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
  private int version;
  private String collection;
  private String externalId;
  private String displayName;
  private String pluralName;
  private String description;
  private List<Property> properties;
  private List<Relation> relations;
  private int usageStatus;
  private long modifiedAt;
  private User modifiedBy;
  private long createdAt;
  private User createdBy;
}
