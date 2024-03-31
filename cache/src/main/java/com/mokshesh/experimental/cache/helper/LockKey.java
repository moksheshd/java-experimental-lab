package com.mokshesh.experimental.cache.helper;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode(of = {"jobId"})
public class LockKey {
  private final Long jobId;
}
