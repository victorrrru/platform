package com.fww;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Set;

/**
 * @author Administrator
 * @date 2018/04/29 18:57
 */
public class RedisSlave {
    private static RedisTemplate<java.io.Serializable, String> redistemplate;
    private static String redisCode = "utf-8";
    private static RedisSlave redisslave = null;

    public static RedisTemplate<java.io.Serializable, String> getRedistemplate() {
        return redistemplate;
    }

    public static void setRedistemplate(RedisTemplate<java.io.Serializable, String> redisTemplate) {
        redistemplate = redisTemplate;
    }

    private RedisSlave() {
    }

    public static RedisSlave getInstance() {
        if(redisslave == null) {
            Class<RedisSlave> var0 = RedisSlave.class;
            synchronized(RedisSlave.class) {
                if(redisslave == null) {
                    redisslave = new RedisSlave();
                }
            }
        }

        return redisslave;
    }

    public long del(final String... keys) {
        return ((Long)redistemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                long result = 0L;

                for(int i = 0; i < keys.length; ++i) {
                    result = connection.del(new byte[][]{keys[i].getBytes()}).longValue();
                }

                return Long.valueOf(result);
            }
        })).longValue();
    }

    public void delete(String... keys) {
        redistemplate.delete(keys);
    }

    public DataType type(String key) {
        return redistemplate.type(key);
    }

    public void set(final byte[] key, final byte[] value, final long liveTime) {
        redistemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(key, value);
                if(liveTime > 0L) {
                    connection.expire(key, liveTime);
                }

                return Long.valueOf(1L);
            }
        });
    }

    public Boolean setNX(final byte[] key, final byte[] value, final long liveTime) {
        return (Boolean)redistemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                Boolean flag = connection.setNX(key, value);
                if(flag.booleanValue() && liveTime > 0L) {
                    connection.expire(key, liveTime);
                }

                return flag;
            }
        });
    }

    public Boolean setNX(String key, String value, long liveTime) {
        return this.setNX(key.getBytes(), value.getBytes(), liveTime);
    }

    public void set(String key, String value, long liveTime) {
        this.set(key.getBytes(), value.getBytes(), liveTime);
    }

    public void setObject(String key, byte[] value, long liveTime) {
        this.set(key.getBytes(), value, liveTime);
    }

    public byte[] get(final String key) {
        return (byte[])((byte[])redistemplate.execute(new RedisCallback() {
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.get(key.getBytes());
            }
        }));
    }

    public String getString(String key) {
        byte[] bytes = this.get(key);
        return bytes != null?new String(this.get(key)):null;
    }

    public void expire(final String key, final long liveTime) {
        redistemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                if(liveTime > 0L) {
                    connection.expire(key.getBytes(), liveTime);
                }

                return Long.valueOf(1L);
            }
        });
    }

    public Set<java.io.Serializable> keys(String pattern) {
        return redistemplate.keys(pattern);
    }

    public Long push(String key, String value) {
        return redistemplate.opsForList().leftPush(key, value);
    }

    public String pop(String key) {
        return redistemplate.opsForList().leftPop(key);
    }

    public Long in(String key, String value) {
        return redistemplate.opsForList().rightPush(key, value);
    }

    public String out(String key) {
        return redistemplate.opsForList().rightPop(key);
    }

    public Long length(String key) {
        return redistemplate.opsForList().size(key);
    }

    public List<String> range(String key, int start, int end) {
        return redistemplate.opsForList().range(key, (long)start, (long)end);
    }

    public void remove(String key, long i, String value) {
        redistemplate.opsForList().remove(key, i, value);
    }

    public String index(String key, long index) {
        return redistemplate.opsForList().index(key, index);
    }

    public void set(String key, long index, String value) {
        redistemplate.opsForList().set(key, index, value);
    }

    public void trim(String key, long start, int end) {
        redistemplate.opsForList().trim(key, start, (long)end);
    }

    public boolean exists(final String key) {
        return (Boolean) redistemplate.execute(new RedisCallback() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] bkey = RedisSlave.redistemplate.getStringSerializer().serialize(key);
                return connection.exists(bkey);
            }
        });
    }

    public boolean hasKey(String key) {
        try {
            return redistemplate.hasKey(key).booleanValue();
        } catch (Exception var3) {
            return false;
        }
    }

    public String flushDB() {
        return (String)redistemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }

    public long dbSize() {
        return (Long) redistemplate.execute(new RedisCallback() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
    }

    public String ping() {
        return (String)redistemplate.execute(new RedisCallback() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.ping();
            }
        });
    }

    public void close() {
        redistemplate.execute(new RedisCallback() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.close();
                return null;
            }
        });
    }
}
