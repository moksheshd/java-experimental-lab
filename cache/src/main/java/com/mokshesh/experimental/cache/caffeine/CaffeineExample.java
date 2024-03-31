package com.mokshesh.experimental.cache.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.mokshesh.experimental.cache.helper.LockKey;

import java.util.concurrent.TimeUnit;

public class CaffeineExample {
  public static final Cache<LockKey, Object> locks = Caffeine.newBuilder()
    .expireAfterAccess(3, TimeUnit.SECONDS)
    .initialCapacity(100)
    .evictionListener((Object key, Object value, RemovalCause cause) -> System.out.println(String.format( "Key %s was evicted (%s)%n", key, cause)))
    .removalListener((Object key, Object value, RemovalCause cause) -> System.out.println(String.format( "Key %s was removed (%s)%n", key, cause)))
    .build();

  private CaffeineExample() {
  }
}
