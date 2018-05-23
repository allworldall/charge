package com.linekong.union.charge.consume.util.cache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class CacheUtil1 {
	
	private static LoadingCache<String, Object> cache = CacheBuilder.newBuilder().maximumSize(1000)  
	           .expireAfterWrite(5, TimeUnit. SECONDS)  
	           .refreshAfterWrite(1, TimeUnit. SECONDS).build(new CacheLoader<String, Object>() {

				@Override
				public Object load(String key) throws Exception {
					return null;
				}
			});
	
	public static void getKey(){
		try {
			cache.get("");
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

}
