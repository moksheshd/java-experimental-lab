package com.mokshesh.experimental.cache.caffeine;

import com.mokshesh.experimental.cache.helper.LockKey;
import org.awaitility.Durations;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CaffeineExampleTest {

  @Test
  void testCacheWithLockKey() throws InterruptedException {
    LockKey key = new LockKey(Long.valueOf(123L));

    // Put an object into the cache if it's not already there
    Object value = CaffeineExample.locks.get(key, k -> new Object());

    // Retrieve the object
    Object retrievedValue = CaffeineExample.locks.getIfPresent(key);

    // Check that the retrieved object is the same as the one we put in
    assertEquals(value, retrievedValue);

    await().atMost(5, TimeUnit.SECONDS);

    // Try to retrieve the object again
    retrievedValue = CaffeineExample.locks.getIfPresent(key);

    // Check that the object is no longer in the cache
    assertNull(retrievedValue);
  }
}
