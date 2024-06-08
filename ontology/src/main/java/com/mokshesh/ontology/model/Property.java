package com.mokshesh.ontology.model;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public class Property {
  private ObjectId _id;
  private String externalId;
  private String displayName;
  private int flags;
  private int sortOrder;
  private List<Option> options;
  private String inputType;
  private int usageStatus;
  private String placeHolder;
  private String description;
}
