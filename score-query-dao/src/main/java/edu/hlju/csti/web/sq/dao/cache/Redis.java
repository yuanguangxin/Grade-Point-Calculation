package edu.hlju.csti.web.sq.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.*;

/**
 * 开发者:李嘉鼎
 * 开发时间:2016/7/12
 * 描述:
 */
@Component
public class Redis {
    private static Logger logger = LoggerFactory.getLogger(Redis.class);
    @Autowired
    private JedisPool jedisPool;

    private Jedis getConnection() {
        return jedisPool.getResource();
    }

    private void returnConnection(Jedis jedis) {
        if (jedis != null) {
            try {
                jedisPool.returnResource(jedis);
            } catch (Exception e) {
                jedisPool.returnBrokenResource(jedis);
            }
        }
    }

    /**
     * 检查key是否存在
     *
     * @param key 键
     * @return 是否存在
     */
    public boolean checkKeyExist(String key) {
        Jedis conn = getConnection();
        if (conn != null) {
            try {
                return conn.exists(key);
            } catch (Exception e) {
                logger.error("error:{}", e);
            } finally {
                returnConnection(conn);
            }
        }
        return false;
    }

    /**
     * 删除key
     *
     * @param key 键
     * @return 删除数量
     */
    public long deleteKey(Serializable key) {
        Jedis conn = getConnection();
        if (conn != null) {
            try {
                return conn.del(serialize(key));
            } catch (Exception e) {
                logger.error("error:{}", e);
            } finally {
                returnConnection(conn);
            }
        }
        return 0L;
    }

    /**
     * 设置键值对缓存
     *
     * @param key   键
     * @param value 值
     * @return 设置状态
     */
    public String set(Serializable key, Serializable value) {
        Jedis conn = getConnection();
        if (conn != null) {
            try {
                return conn.set(serialize(key), serialize(value));
            } catch (Exception e) {
                logger.error("error:{}", e);
            } finally {
                returnConnection(conn);
            }
        }
        return null;
    }

    @SuppressWarnings("ALL")
    public <T> T get(Serializable key) {
        Jedis conn = getConnection();
        if (conn != null) {
            try {
                byte[] bytes = conn.get(serialize(key));
                Object object = unSerialize(bytes);
                if (object != null) {
                    return (T) object;
                }
            } catch (Exception e) {
                logger.error("error:{}", e);
            } finally {
                returnConnection(conn);
            }
        }
        return null;
    }

    @SuppressWarnings("ALL")
    private byte[] serialize(Serializable object) {
        if (object == null) {
            return null;
        }
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                logger.error("Error:{}", e);
            }
        }
        return null;
    }

    @SuppressWarnings("ALL")
    private Object unSerialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Error:{}", e);
        } finally {
            try {
                if (bais != null) {
                    bais.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                logger.error("Error:{}", e);
            }
        }
        return null;
    }
}
