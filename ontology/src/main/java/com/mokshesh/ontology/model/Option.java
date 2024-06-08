package com.mokshesh.ontology.model;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class Option {
  private ObjectId _id;
  private String displayName;
}
