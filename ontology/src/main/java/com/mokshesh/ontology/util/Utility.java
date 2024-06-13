package com.mokshesh.ontology.util;

import com.mokshesh.ontology.model.*;
import org.bson.Document;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Utility {
  public static final int ARCHIVED = 7;

  private Utility() {
  }

  private static long getLongValue(Document document, String key) {
    Number number = document.get(key, Number.class);
    return number == null ? 0L : number.longValue();
  }

  public static ObjectType convertDocumentToObjectType(Document document) {
    ObjectType objectType = new ObjectType();

    objectType.setId(document.getObjectId("_id"));
    objectType.setVersion(document.getInteger("version", 0));
    objectType.setCollection(document.getString("collection"));
    objectType.setExternalId(document.getString("externalId"));
    objectType.setDisplayName(document.getString("displayName"));
    objectType.setPluralName(document.getString("pluralName"));
    objectType.setDescription(document.getString("description"));

    List<Document> propertiesDocs = document.getList("properties", Document.class);
    List<Property> properties = new ArrayList<>();
    for (Document propDoc : propertiesDocs) {
      Property property = new Property();
      property.setId(propDoc.getObjectId("_id"));
      property.setExternalId(propDoc.getString("externalId"));
      property.setDisplayName(propDoc.getString("displayName"));
      property.setFlags(propDoc.getInteger("flags", 0));
      property.setSortOrder(propDoc.getInteger("sortOrder", 0));

      List<Document> optionsDocs = propDoc.getList("options", Document.class);
      if (optionsDocs == null) {
        optionsDocs = new ArrayList<>();
      }
      List<Option> options = new ArrayList<>();
      for (Document optDoc : optionsDocs) {
        Option option = new Option();
        option.setId(optDoc.get("_id").toString());
        option.setDisplayName(optDoc.getString("displayName"));
        options.add(option);
      }
      property.setOptions(options);

      property.setInputType(propDoc.getString("inputType"));
      property.setUsageStatus(propDoc.getInteger("usageStatus", 0));
      property.setPlaceHolder(propDoc.getString("placeHolder"));
      property.setDescription(propDoc.getString("description"));

      properties.add(property);
    }
    objectType.setProperties(properties);

    List<Document> relationsDocs = document.getList("relations", Document.class);
    List<Relation> relations = new ArrayList<>();
    for (Document relDoc : relationsDocs) {
      Relation relation = new Relation();
      relation.setId(relDoc.getObjectId("_id"));
      relation.setExternalId(relDoc.getString("externalId"));
      relation.setDisplayName(relDoc.getString("displayName"));
      relation.setUsable(relDoc.getBoolean("usable", false));

      Document targetDoc = relDoc.get("target", Document.class);
      if (targetDoc != null) {
        Target target = new Target();
        target.setType(targetDoc.getString("type"));
        target.setCardinality(targetDoc.getString("cardinality"));
        target.setUrlPath(targetDoc.getString("urlPath"));
        relation.setTarget(target);
      }

      relation.setSortOrder(relDoc.getInteger("sortOrder", 0));
      relation.setDescription(relDoc.getString("description"));
      relation.setUsageStatus(relDoc.getInteger("usageStatus", 0));
      relation.setObjectTypeId(relDoc.getString("objectTypeId"));
      relation.setFlags(relDoc.getInteger("flags", 0));

      relations.add(relation);
    }
    objectType.setRelations(relations);
    objectType.setUsageStatus(document.getInteger("usageStatus", 0));
    objectType.setModifiedAt(getLongValue(document, "modifiedAt"));

    Document modifiedByDoc = document.get("modifiedBy", Document.class);
    if (modifiedByDoc != null) {
      User modifiedBy = new User();
      modifiedBy.set_id(modifiedByDoc.getString("_id"));
      modifiedBy.setEmployeeId(modifiedByDoc.getString("employeeId"));
      modifiedBy.setFirstName(modifiedByDoc.getString("firstName"));
      modifiedBy.setLastName(modifiedByDoc.getString("lastName"));
      objectType.setModifiedBy(modifiedBy);
    }

    objectType.setCreatedAt(getLongValue(document, "createdAt"));

    Document createdByDoc = document.get("createdBy", Document.class);
    if (createdByDoc != null) {
      User createdBy = new User();
      createdBy.set_id(createdByDoc.getString("_id"));
      createdBy.setEmployeeId(createdByDoc.getString("employeeId"));
      createdBy.setFirstName(createdByDoc.getString("firstName"));
      createdBy.setLastName(createdByDoc.getString("lastName"));
      objectType.setCreatedBy(createdBy);
    }
    return objectType;
  }

  public static void export(String fileName, List<ObjectType> objectTypes) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd.HHmm");
    LocalDateTime now = LocalDateTime.now();
    fileName = String.format("%s_%s.csv", fileName, dtf.format(now));
    try (FileWriter writer = new FileWriter(fileName)) {
      // Write the header
      writer.append("ObjectType ID,ObjectType Name,Property ID,Property Name,Option ID,Option Name,Relation ID,Relation Name,Relation ObjectType ID,Relation External ID,Is Relation?\n");

      for (ObjectType objectType : objectTypes) {
        if (objectType.getUsageStatus() != ARCHIVED) {
          String objectTypeId = objectType.getId().toString();
          String objectTypeDisplayName = objectType.getDisplayName();

          // Write properties and options
          if (objectType.getProperties() != null) {
            for (Property property : objectType.getProperties()) {
              if (property.getUsageStatus() != ARCHIVED) {
                if (property.getOptions() != null && !property.getOptions().isEmpty()) {
                  for (Option option : property.getOptions()) {
                    writer.append(objectTypeId)
                      .append(",")
                      .append(objectTypeDisplayName)
                      .append(",")
                      .append(property.getId().toString())
                      .append(",")
                      .append(property.getDisplayName())
                      .append(",")
                      .append(option.getId())
                      .append(",")
                      .append(option.getDisplayName())
                      .append(",,,\n");
                  }
                } else {
                  writer.append(objectTypeId)
                    .append(",")
                    .append(objectTypeDisplayName)
                    .append(",")
                    .append(property.getId().toString())
                    .append(",")
                    .append(property.getDisplayName())
                    .append(",,,,,,\n");
                }
              }

            }
          }

          // Write relations
          if (objectType.getRelations() != null) {
            for (Relation relation : objectType.getRelations()) {
              writer.append(objectTypeId)
                .append(",")
                .append(objectTypeDisplayName)
                .append(",,,,,")
                .append(relation.getId().toString())
                .append(",")
                .append(relation.getDisplayName())
                .append(",")
                .append(relation.getObjectTypeId())
                .append(",")
                .append(relation.getExternalId())
                .append(",true\n");
            }
          }

        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }


  }
}
