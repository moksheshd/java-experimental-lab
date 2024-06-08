package com.mokshesh.ontology.model;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class Relation {
  private ObjectId _id;
  private String externalId;
  private String displayName;
  private boolean usable;
  private Target target;
  private int sortOrder;
  private String description;
  private int usageStatus;
  private String objectTypeId;
  private int flags;
}
