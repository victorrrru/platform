package com.fww.redis;

import com.fww.utils.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

import java.util.*;

/**
 * @author 范文武
 * @date 2018/05/03 09:49
 */
public class ShardedJedisService {
    public static Logger logger = LoggerFactory.getLogger(ShardedJedisService.class);
    private ShardedJedisPool shardedJedisPool;

    public ShardedJedisService() {
    }

    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }

    public long expire(String key, int seconds) {
        if(key != null && !key.equals("")) {
            ShardedJedis shardedJedis = null;

            try {
                shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
                long var4 = shardedJedis.expire(key, seconds).longValue();
                return var4;
            } catch (Exception var9) {
                logger.error("EXPIRE error[key=" + key + " seconds=" + seconds + "]" + var9.getMessage(), var9);
                this.returnBrokenResource(shardedJedis);
            } finally {
                this.returnResource(shardedJedis);
            }

            return 0L;
        } else {
            return 0L;
        }
    }

    public long expireAt(String key, long unixTimestamp) {
        if(key != null && !key.equals("")) {
            ShardedJedis shardedJedis = null;

            try {
                shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
                long var5 = shardedJedis.expireAt(key, unixTimestamp).longValue();
                return var5;
            } catch (Exception var10) {
                logger.error("EXPIRE error[key=" + key + " unixTimestamp=" + unixTimestamp + "]" + var10.getMessage(), var10);
                this.returnBrokenResource(shardedJedis);
            } finally {
                this.returnResource(shardedJedis);
            }

            return 0L;
        } else {
            return 0L;
        }
    }

    public String trimList(String key, long start, long end) {
        if(key != null && !key.equals("")) {
            ShardedJedis shardedJedis = null;

            try {
                shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
                String var7 = shardedJedis.ltrim(key, start, end);
                return var7;
            } catch (Exception var11) {
                logger.error("LTRIM 出错[key=" + key + " start=" + start + " end=" + end + "]" + var11.getMessage(), var11);
                this.returnBrokenResource(shardedJedis);
            } finally {
                this.returnResource(shardedJedis);
            }

            return "-";
        } else {
            return "-";
        }
    }

    public long countSet(String key) {
        if(key == null) {
            return 0L;
        } else {
            ShardedJedis shardedJedis = null;

            try {
                shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
                long var3 = shardedJedis.scard(key).longValue();
                return var3;
            } catch (Exception var8) {
                logger.error("countSet error.", var8);
                this.returnBrokenResource(shardedJedis);
            } finally {
                this.returnResource(shardedJedis);
            }

            return 0L;
        }
    }

    public boolean addSet(String key, int seconds, String... value) {
        boolean result = this.addSet(key, value);
        if(result) {
            long i = this.expire(key, seconds);
            return i == 1L;
        } else {
            return false;
        }
    }

    public boolean addSet(String key, String... value) {
        if(key != null && value != null) {
            ShardedJedis shardedJedis = null;

            try {
                shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
                shardedJedis.sadd(key, value);
                boolean var4 = true;
                return var4;
            } catch (Exception var8) {
                logger.error("setList error.", var8);
                this.returnBrokenResource(shardedJedis);
            } finally {
                this.returnResource(shardedJedis);
            }

            return false;
        } else {
            return false;
        }
    }

    public boolean containsInSet(String key, String value) {
        if(key != null && value != null) {
            ShardedJedis shardedJedis = null;

            try {
                shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
                boolean var4 = shardedJedis.sismember(key, value).booleanValue();
                return var4;
            } catch (Exception var8) {
                logger.error("setList error.", var8);
                this.returnBrokenResource(shardedJedis);
            } finally {
                this.returnResource(shardedJedis);
            }

            return false;
        } else {
            return false;
        }
    }

    public Set<String> getSet(String key) {
        ShardedJedis shardedJedis = null;

        try {
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            Set var3 = shardedJedis.smembers(key);
            return var3;
        } catch (Exception var7) {
            logger.error("getList error.", var7);
            this.returnBrokenResource(shardedJedis);
        } finally {
            this.returnResource(shardedJedis);
        }

        return null;
    }

    public boolean removeSetValue(String key, String... value) {
        ShardedJedis shardedJedis = null;

        try {
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            shardedJedis.srem(key, value);
            boolean var4 = true;
            return var4;
        } catch (Exception var8) {
            logger.error("getList error.", var8);
            this.returnBrokenResource(shardedJedis);
        } finally {
            this.returnResource(shardedJedis);
        }

        return false;
    }

    public int removeListValue(String key, List<String> values) {
        return this.removeListValue(key, 1L, values);
    }

    public int removeListValue(String key, long count, List<String> values) {
        int result = 0;
        if(values != null && values.size() > 0) {
            Iterator var6 = values.iterator();

            while(var6.hasNext()) {
                String value = (String)var6.next();
                if(this.removeListValue(key, count, value)) {
                    ++result;
                }
            }
        }

        return result;
    }

    public boolean removeListValue(String key, long count, String value) {
        ShardedJedis shardedJedis = null;

        try {
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            shardedJedis.lrem(key, count, value);
            boolean var6 = true;
            return var6;
        } catch (Exception var10) {
            logger.error("getList error.", var10);
            this.returnBrokenResource(shardedJedis);
        } finally {
            this.returnResource(shardedJedis);
        }

        return false;
    }

    public List<String> rangeList(String key, long start, long end) {
        if(key != null && !key.equals("")) {
            ShardedJedis shardedJedis = null;

            try {
                shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
                List var7 = shardedJedis.lrange(key, start, end);
                return var7;
            } catch (Exception var11) {
                logger.error("rangeList 出错[key=" + key + " start=" + start + " end=" + end + "]" + var11.getMessage(), var11);
                this.returnBrokenResource(shardedJedis);
            } finally {
                this.returnResource(shardedJedis);
            }

            return null;
        } else {
            return null;
        }
    }

    public long countList(String key) {
        if(key == null) {
            return 0L;
        } else {
            ShardedJedis shardedJedis = null;

            try {
                shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
                long var3 = shardedJedis.llen(key).longValue();
                return var3;
            } catch (Exception var8) {
                logger.error("countList error.", var8);
                this.returnBrokenResource(shardedJedis);
            } finally {
                this.returnResource(shardedJedis);
            }

            return 0L;
        }
    }

    public boolean addList(String key, int seconds, String... value) {
        boolean result = this.addList(key, value);
        if(result) {
            long i = this.expire(key, seconds);
            return i == 1L;
        } else {
            return false;
        }
    }

    public boolean addList(String key, String... value) {
        if(key != null && value != null) {
            ShardedJedis shardedJedis = null;

            try {
                shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
                shardedJedis.lpush(key, value);
                boolean var4 = true;
                return var4;
            } catch (Exception var8) {
                logger.error("setList error.", var8);
                this.returnBrokenResource(shardedJedis);
            } finally {
                this.returnResource(shardedJedis);
            }

            return false;
        } else {
            return false;
        }
    }

    public boolean addList(String key, List<String> list) {
        if(key != null && list != null && list.size() != 0) {
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                String value = (String)var3.next();
                this.addList(key, new String[]{value});
            }

            return true;
        } else {
            return false;
        }
    }

    public List<String> getList(String key) {
        ShardedJedis shardedJedis = null;

        try {
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            List var3 = shardedJedis.lrange(key, 0L, -1L);
            return var3;
        } catch (Exception var7) {
            logger.error("getList error.", var7);
            this.returnBrokenResource(shardedJedis);
        } finally {
            this.returnResource(shardedJedis);
        }

        return null;
    }

    public boolean setHSet(String domain, String key, String value) {
        if(value == null) {
            return false;
        } else {
            ShardedJedis shardedJedis = null;

            try {
                shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
                shardedJedis.hset(domain, key, value);
                boolean var5 = true;
                return var5;
            } catch (Exception var9) {
                logger.error("setHSet error.", var9);
                this.returnBrokenResource(shardedJedis);
            } finally {
                this.returnResource(shardedJedis);
            }

            return false;
        }
    }

    public String getHSet(String domain, String key) {
        ShardedJedis shardedJedis = null;

        try {
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            String var4 = shardedJedis.hget(domain, key);
            return var4;
        } catch (Exception var8) {
            logger.error("getHSet error.", var8);
            this.returnBrokenResource(shardedJedis);
        } finally {
            this.returnResource(shardedJedis);
        }

        return null;
    }

    public long delHSet(String domain, String key) {
        ShardedJedis shardedJedis = null;
        long count = 0L;

        try {
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            count = shardedJedis.hdel(domain, new String[]{key}).longValue();
        } catch (Exception var10) {
            logger.error("delHSet error.", var10);
            this.returnBrokenResource(shardedJedis);
        } finally {
            this.returnResource(shardedJedis);
        }

        return count;
    }

    public long delHSet(String domain, String... key) {
        ShardedJedis shardedJedis = null;
        long count = 0L;

        try {
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            count = shardedJedis.hdel(domain, key).longValue();
        } catch (Exception var10) {
            logger.error("delHSet error.", var10);
            this.returnBrokenResource(shardedJedis);
        } finally {
            this.returnResource(shardedJedis);
        }

        return count;
    }

    public boolean existsHSet(String domain, String key) {
        ShardedJedis shardedJedis = null;
        boolean isExist = false;

        try {
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            isExist = shardedJedis.hexists(domain, key).booleanValue();
        } catch (Exception var9) {
            logger.error("existsHSet error.", var9);
            this.returnBrokenResource(shardedJedis);
        } finally {
            this.returnResource(shardedJedis);
        }

        return isExist;
    }

    public List<Map.Entry<String, String>> scanHSet(String domain, String match) {
        ShardedJedis shardedJedis = null;

        try {
            int cursor = 0;
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            ScanParams scanParams = new ScanParams();
            scanParams.match(match);
            Jedis jedis = (Jedis)shardedJedis.getShard(domain);
            ArrayList list = new ArrayList();

            do {
                ScanResult<Map.Entry<String, String>> scanResult = jedis.hscan(domain, String.valueOf(cursor), scanParams);
                list.addAll(scanResult.getResult());
                cursor = Integer.parseInt(scanResult.getStringCursor());
            } while(cursor > 0);

            ArrayList var9 = list;
            return var9;
        } catch (Exception var13) {
            logger.error("scanHSet error.", var13);
            this.returnBrokenResource(shardedJedis);
        } finally {
            this.returnResource(shardedJedis);
        }

        return null;
    }

    public List<String> hvals(String domain) {
        ShardedJedis shardedJedis = null;
        List retList = null;

        try {
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            retList = shardedJedis.hvals(domain);
        } catch (Exception var8) {
            logger.error("hvals error.", var8);
            this.returnBrokenResource(shardedJedis);
        } finally {
            this.returnResource(shardedJedis);
        }

        return retList;
    }

    public Set<String> hkeys(String domain) {
        ShardedJedis shardedJedis = null;
        Set retList = null;

        try {
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            retList = shardedJedis.hkeys(domain);
        } catch (Exception var8) {
            logger.error("hkeys error.", var8);
            this.returnBrokenResource(shardedJedis);
        } finally {
            this.returnResource(shardedJedis);
        }

        return retList;
    }

    public long lenHset(String domain) {
        ShardedJedis shardedJedis = null;
        long retList = 0L;

        try {
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            retList = shardedJedis.hlen(domain).longValue();
        } catch (Exception var9) {
            logger.error("hkeys error.", var9);
            this.returnBrokenResource(shardedJedis);
        } finally {
            this.returnResource(shardedJedis);
        }

        return retList;
    }

    public boolean setSortedSet(String key, long score, String value) {
        ShardedJedis shardedJedis = null;

        try {
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            shardedJedis.zadd(key, (double)score, value);
            boolean var6 = true;
            return var6;
        } catch (Exception var10) {
            logger.error("setSortedSet error.", var10);
            this.returnBrokenResource(shardedJedis);
        } finally {
            this.returnResource(shardedJedis);
        }

        return false;
    }

    public Set<String> getSoredSet(String key, long startScore, long endScore, boolean orderByDesc) {
        ShardedJedis shardedJedis = null;

        Set var8;
        try {
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            if(orderByDesc) {
                var8 = shardedJedis.zrevrangeByScore(key, (double)endScore, (double)startScore);
                return var8;
            }

            var8 = shardedJedis.zrangeByScore(key, (double)startScore, (double)endScore);
        } catch (Exception var12) {
            logger.error("getSoredSet error.", var12);
            this.returnBrokenResource(shardedJedis);
            return null;
        } finally {
            this.returnResource(shardedJedis);
        }

        return var8;
    }

    public long countSoredSet(String key, long startScore, long endScore) {
        ShardedJedis shardedJedis = null;

        try {
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            Long count = shardedJedis.zcount(key, (double)startScore, (double)endScore);
            long var8 = count == null?0L:count.longValue();
            return var8;
        } catch (Exception var13) {
            logger.error("countSoredSet error.", var13);
            this.returnBrokenResource(shardedJedis);
        } finally {
            this.returnResource(shardedJedis);
        }

        return 0L;
    }

    public boolean delSortedSet(String key, String value) {
        ShardedJedis shardedJedis = null;

        try {
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            long count = shardedJedis.zrem(key, new String[]{value}).longValue();
            boolean var6 = count > 0L;
            return var6;
        } catch (Exception var10) {
            logger.error("delSortedSet error.", var10);
            this.returnBrokenResource(shardedJedis);
        } finally {
            this.returnResource(shardedJedis);
        }

        return false;
    }

    public Set<String> getSoredSetByRange(String key, int startRange, int endRange, boolean orderByDesc) {
        ShardedJedis shardedJedis = null;

        Set var6;
        try {
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            if(orderByDesc) {
                var6 = shardedJedis.zrevrange(key, (long)startRange, (long)endRange);
                return var6;
            }

            var6 = shardedJedis.zrange(key, (long)startRange, (long)endRange);
        } catch (Exception var10) {
            logger.error("getSoredSetByRange error.", var10);
            this.returnBrokenResource(shardedJedis);
            return null;
        } finally {
            this.returnResource(shardedJedis);
        }

        return var6;
    }

    public Double getScore(String key, String member) {
        ShardedJedis shardedJedis = null;

        try {
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            Double var4 = shardedJedis.zscore(key, member);
            return var4;
        } catch (Exception var8) {
            logger.error("getSoredSet error.", var8);
            this.returnBrokenResource(shardedJedis);
        } finally {
            this.returnResource(shardedJedis);
        }

        return null;
    }

    public boolean set(String key, String value, int second) {
        ShardedJedis shardedJedis = null;

        try {
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            shardedJedis.setex(key, second, value);
            boolean var5 = true;
            return var5;
        } catch (Exception var9) {
            logger.error("set error.", var9);
            this.returnBrokenResource(shardedJedis);
        } finally {
            this.returnResource(shardedJedis);
        }

        return false;
    }

    public boolean set(String key, String value) {
        ShardedJedis shardedJedis = null;

        try {
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            shardedJedis.set(key, value);
            boolean var4 = true;
            return var4;
        } catch (Exception var8) {
            logger.error("set error.", var8);
            this.returnBrokenResource(shardedJedis);
        } finally {
            this.returnResource(shardedJedis);
        }

        return false;
    }

    public Object getValue(String key) {
        Object ret = null;
        ShardedJedis jedis = (ShardedJedis)this.shardedJedisPool.getResource();

        try {
            byte[] obj = jedis.get(key.getBytes());
            if(obj != null) {
                ret = SerializeUtil.unserialize(obj);
            }
        } catch (Exception var8) {
            jedis.disconnect();
        } finally {
            jedis.disconnect();
        }

        return ret;
    }

    public String get(String key, String defaultValue) {
        ShardedJedis shardedJedis = null;

        try {
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            String var4 = shardedJedis.get(key) == null?defaultValue:shardedJedis.get(key);
            return var4;
        } catch (Exception var8) {
            logger.error("get error.", var8);
            this.returnBrokenResource(shardedJedis);
        } finally {
            this.returnResource(shardedJedis);
        }

        return defaultValue;
    }

    public boolean del(String key) {
        ShardedJedis shardedJedis = null;

        try {
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            shardedJedis.del(key);
            boolean var3 = true;
            return var3;
        } catch (Exception var7) {
            logger.error("del error.", var7);
            this.returnBrokenResource(shardedJedis);
        } finally {
            this.returnResource(shardedJedis);
        }

        return false;
    }

    public long incr(String key) {
        ShardedJedis shardedJedis = null;

        try {
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            long var3 = shardedJedis.incr(key).longValue();
            return var3;
        } catch (Exception var8) {
            logger.error("incr error.", var8);
            this.returnBrokenResource(shardedJedis);
        } finally {
            this.returnResource(shardedJedis);
        }

        return 0L;
    }

    public long decr(String key) {
        ShardedJedis shardedJedis = null;

        try {
            shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            long var3 = shardedJedis.decr(key).longValue();
            return var3;
        } catch (Exception var8) {
            logger.error("incr error.", var8);
            this.returnBrokenResource(shardedJedis);
        } finally {
            this.returnResource(shardedJedis);
        }

        return 0L;
    }

    private void returnBrokenResource(ShardedJedis shardedJedis) {
        if(shardedJedis != null) {
            try {
                this.shardedJedisPool.returnBrokenResource(shardedJedis);
            } catch (Exception var3) {
                logger.error("returnBrokenResource error.", var3);
            }

        }
    }

    private void returnResource(ShardedJedis shardedJedis) {
        try {
            this.shardedJedisPool.returnResource(shardedJedis);
        } catch (Exception var3) {
            logger.error("returnResource error.", var3);
        }

    }

    public void returnResource(ShardedJedis shardedJedis, boolean broken) {
        if(broken) {
            this.shardedJedisPool.returnBrokenResource(shardedJedis);
        } else {
            this.shardedJedisPool.returnResource(shardedJedis);
        }

    }

    public void disconnect() {
        try {
            ShardedJedis shardedJedis = (ShardedJedis)this.shardedJedisPool.getResource();
            shardedJedis.disconnect();
        } catch (Exception var2) {
            logger.error("shardedJedis disconnect error.", var2);
        }

    }
}
