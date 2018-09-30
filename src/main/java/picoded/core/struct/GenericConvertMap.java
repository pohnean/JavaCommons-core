package picoded.core.struct;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.Set;
import java.util.Collections;
import java.util.HashSet;

import picoded.core.conv.GenericConvert;
import picoded.core.conv.NestedObjectUtil;
import picoded.core.conv.NestedObjectFetch;
import picoded.core.struct.template.UnsupportedDefaultMap;

/**
 * Common map class, used to implement all the generic convert convinence functions in a map interface
 **/
public interface GenericConvertMap<K, V> extends UnsupportedDefaultMap<K, V> {
	
	// Static proxy build
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * Ensures the returned map is a GenericConvertMap, doing the conversion
	 * if needed
	 **/
	static <A, B> GenericConvertMap<A, B> build(Map<A, B> inMap) {
		return ProxyGenericConvertMap.ensure(inMap);
	}
	
	//---------------------------------------------------------------------------------------------------
	//
	//  Basic get and fetch operations
	//
	//---------------------------------------------------------------------------------------------------
	
	// Fallback if null, for native format
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * Map get function, with fallback
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable, aka null)
	 *
	 * @return The converted string, always possible unless null
	 **/
	default V get(K key, V fallbck) {
		V res = get(key);
		if (res == null) {
			return fallbck;
		}
		return res;
	}
	
	// NESTED object fetch 
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * Gets an object from the map,
	 * That could very well be, a map inside a list, inside a map, inside a
	 * .....
	 *
	 * Note that at each iteration step, it attempts to do a FULL key match
	 * first, before the next iteration depth.
	 *
	 * @param base Map / List to manipulate from
	 * @param key The input key to fetch, possibly nested
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The fetched object, always possible unless fallbck null
	 **/
	default Object fetchObject(String key, Object fallbck) {
		return NestedObjectFetch.fetchObject(this, key, fallbck);
	}
	
	/**
	 * Default Null fallback, for `fetchObject(key,fallback)`
	 *
	 * @param base Map / List to manipulate from
	 * @param key The input key to fetch, possibly nested
	 *
	 * @return The fetched object, always possible unless fallbck null
	 **/
	default Object fetchObject(String key) {
		//return fetchObject(key, null);
		return fetchObject(key, null);
	}
	
	//---------------------------------------------------------------------------------------------------
	//
	//  GET operations with conversions
	//
	//---------------------------------------------------------------------------------------------------
	
	// to string conversion
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To String conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable, aka null)
	 *
	 * @return The converted string, always possible unless null
	 **/
	default String getString(K key, String fallbck) {
		return GenericConvert.toString(get(key), fallbck);
	}
	
	/**
	 * Default null fallback, To String conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted string, always possible unless null
	 **/
	default String getString(K key) {
		return GenericConvert.toString(get(key));
	}
	
	// to boolean conversion
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To boolean conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted string, always possible unless null
	 **/
	default boolean getBoolean(K key, boolean fallbck) {
		return GenericConvert.toBoolean(get(key), fallbck);
	}
	
	/**
	 * Default boolean fallback, To String conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted string, always possible unless null
	 **/
	default boolean getBoolean(K key) {
		return GenericConvert.toBoolean(get(key));
	}
	
	// to Number conversion
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To Number conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted string, always possible unless null
	 **/
	default Number getNumber(K key, Number fallbck) {
		return GenericConvert.toNumber(get(key), fallbck);
	}
	
	/**
	 * Default Number fallback, To String conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted string, always possible unless null
	 **/
	default Number getNumber(K key) {
		return GenericConvert.toNumber(get(key));
	}
	
	// to int conversion
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To int conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted string, always possible unless null
	 **/
	default int getInt(K key, int fallbck) {
		return GenericConvert.toInt(get(key), fallbck);
	}
	
	/**
	 * Default int fallback, To String conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted string, always possible unless null
	 **/
	default int getInt(K key) {
		return GenericConvert.toInt(get(key));
	}
	
	// to long conversion
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To long conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted string, always possible unless null
	 **/
	default long getLong(K key, long fallbck) {
		return GenericConvert.toLong(get(key), fallbck);
	}
	
	/**
	 * Default long fallback, To String conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted string, always possible unless null
	 **/
	default long getLong(K key) {
		return GenericConvert.toLong(get(key));
	}
	
	// to float conversion
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To float conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted string, always possible unless null
	 **/
	default float getFloat(K key, float fallbck) {
		return GenericConvert.toFloat(get(key), fallbck);
	}
	
	/**
	 * Default float fallback, To String conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted string, always possible unless null
	 **/
	default float getFloat(K key) {
		return GenericConvert.toFloat(get(key));
	}
	
	// to double conversion
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To double conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted string, always possible unless null
	 **/
	default double getDouble(K key, double fallbck) {
		return GenericConvert.toDouble(get(key), fallbck);
	}
	
	/**
	 * Default float fallback, To String conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted string, always possible unless null
	 **/
	default double getDouble(K key) {
		return GenericConvert.toDouble(get(key));
	}
	
	// to byte conversion
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To byte conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted string, always possible unless null
	 **/
	default byte getByte(K key, byte fallbck) {
		return GenericConvert.toByte(get(key), fallbck);
	}
	
	/**
	 * Default float fallback, To String conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted string, always possible unless null
	 **/
	default byte getByte(K key) {
		return GenericConvert.toByte(get(key));
	}
	
	// to short conversion
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To short conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted string, always possible unless null
	 **/
	default short getShort(K key, short fallbck) {
		return GenericConvert.toShort(get(key), fallbck);
	}
	
	/**
	 * Default short fallback, To String conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted string, always possible unless null
	 **/
	default short getShort(K key) {
		return GenericConvert.toShort(get(key));
	}
	
	// to UUID / GUID
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To UUID conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted UUID, always possible unless null
	 **/
	default UUID getUUID(K key, Object fallbck) {
		return GenericConvert.toUUID(get(key), fallbck);
	}
	
	/**
	 * Default Null fallback, To UUID conversion of generic object
	 *
	 * @param input The input value to convert
	 *
	 * @return The converted value
	 **/
	default UUID getUUID(K key) {
		return GenericConvert.toUUID(get(key));
	}
	
	/**
	 * To GUID conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted UUID, always possible unless null
	 **/
	default String getGUID(K key, Object fallbck) {
		return GenericConvert.toGUID(get(key), fallbck);
	}
	
	/**
	 * Default Null fallback, To GUID conversion of generic object
	 *
	 * @param input The input value to convert
	 *
	 * @return The converted value
	 **/
	default String getGUID(K key) {
		return GenericConvert.toGUID(get(key));
	}
	
	/**
	 * To List<Object> conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted Object[], always possible unless null
	 **/
	default List<Object> getObjectList(K key, Object fallbck) {
		return GenericConvert.toList(get(key), fallbck);
	}
	
	/**
	 * Default Null fallback, To List<Object> conversion of generic object
	 *
	 * @param input The input value to convert
	 *
	 * @default The converted value
	 **/
	default List<Object> getObjectList(K key) {
		return GenericConvert.toList(get(key));
	}
	
	/// To List<V> conversion of generic object
	///
	/// @param key The input value key to convert
	/// @param fallbck The fallback default (if not convertable)
	///
	/// @returns The converted Object[], always possible unless null
	default <V> List<V> getList(K key, Object fallbck) {
		return GenericConvert.toList(get(key), fallbck);
	}
	
	/// Default Null fallback, To List<V> conversion of generic object
	///
	/// @param input The input value to convert
	///
	/// @default The converted value
	default <V> List<V> getList(K key) {
		return GenericConvert.toList(get(key));
	}
	
	// to map
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To String Map conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted Map if possible, else null
	 **/
	default <K extends String, V> Map<K, V> getStringMap(K key, Object fallbck) {
		return GenericConvert.toStringMap(get(key), fallbck);
	}
	
	/**
	 * Default Null fallback, To String Map conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted Map if possible, else null
	 **/
	default <K extends String, V> Map<K, V> getStringMap(K key) {
		return GenericConvert.toStringMap(get(key));
	}
	
	/**
	 * To String Map conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted Map if possible, else null
	 **/
	default <K extends String, V> GenericConvertMap<K, V> getGenericConvertStringMap(K key,
		Object fallbck) {
		return GenericConvert.toGenericConvertStringMap(get(key), fallbck);
	}
	
	/**
	 * Default Null fallback, To String Map conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted Map if possible, else null
	 **/
	default <K extends String, V> GenericConvertMap<K, V> getGenericConvertStringMap(K key) {
		return GenericConvert.toGenericConvertStringMap(get(key));
	}
	
	// to array
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To String Map conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted Map if possible, else null
	 **/
	default GenericConvertList<V> getGenericConvertList(K key, Object fallbck) {
		return GenericConvert.toGenericConvertList(get(key), fallbck);
	}
	
	/**
	 * Default Null fallback, To String Map conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted Map if possible, else null
	 **/
	default GenericConvertList<V> getGenericConvertList(K key) {
		return GenericConvert.toGenericConvertList(get(key));
	}
	
	// to string array
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To String[] conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param The fallback default (if not convertable)
	 *
	 * @return The converted String[], always possible unless null
	 **/
	default String[] getStringArray(K key, Object fallbck) {
		return GenericConvert.toStringArray(get(key), fallbck);
	}
	
	/**
	 * Default Null fallback, To String[] conversion of generic object
	 *
	 * @param input The input value to convert
	 *
	 * @return The converted value
	 **/
	default String[] getStringArray(K key) {
		return getStringArray(key, null);
	}
	
	// to object array
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To Object[] conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted Object[], always possible unless null
	 **/
	default Object[] getObjectArray(K key, Object fallbck) {
		return GenericConvert.toObjectArray(get(key), fallbck);
	}
	
	/**
	 * Default Null fallback, To Object[] conversion of generic object
	 *
	 * @param input The input value to convert
	 *
	 * @default The converted value
	 **/
	default Object[] getObjectArray(K key) {
		return GenericConvert.toObjectArray(get(key));
	}
	
	//---------------------------------------------------------------------------------------------------
	//
	//  FETCH operations with conversions
	//
	//---------------------------------------------------------------------------------------------------
	
	// to string conversion
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To String conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable, aka null)
	 *
	 * @return The converted string, always possible unless null
	 **/
	default String fetchString(String key, String fallbck) {
		return GenericConvert.toString(fetchObject(key), fallbck);
	}
	
	/**
	 * Default null fallback, To String conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted string, always possible unless null
	 **/
	default String fetchString(String key) {
		return GenericConvert.toString(fetchObject(key));
	}
	
	// to boolean conversion
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To boolean conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted string, always possible unless null
	 **/
	default boolean fetchBoolean(String key, boolean fallbck) {
		return GenericConvert.toBoolean(fetchObject(key), fallbck);
	}
	
	/**
	 * Default boolean fallback, To String conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted string, always possible unless null
	 **/
	default boolean fetchBoolean(String key) {
		return GenericConvert.toBoolean(fetchObject(key));
	}
	
	// to Number conversion
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To Number conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted string, always possible unless null
	 **/
	default Number fetchNumber(String key, Number fallbck) {
		return GenericConvert.toNumber(fetchObject(key), fallbck);
	}
	
	/**
	 * Default Number fallback, To String conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted string, always possible unless null
	 **/
	default Number fetchNumber(String key) {
		return GenericConvert.toNumber(fetchObject(key));
	}
	
	// to int conversion
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To int conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted string, always possible unless null
	 **/
	default int fetchInt(String key, int fallbck) {
		return GenericConvert.toInt(fetchObject(key), fallbck);
	}
	
	/**
	 * Default int fallback, To String conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted string, always possible unless null
	 **/
	default int fetchInt(String key) {
		return GenericConvert.toInt(fetchObject(key));
	}
	
	// to long conversion
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To long conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted string, always possible unless null
	 **/
	default long fetchLong(String key, long fallbck) {
		return GenericConvert.toLong(fetchObject(key), fallbck);
	}
	
	/**
	 * Default long fallback, To String conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted string, always possible unless null
	 **/
	default long fetchLong(String key) {
		return GenericConvert.toLong(fetchObject(key));
	}
	
	// to float conversion
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To float conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted string, always possible unless null
	 **/
	default float fetchFloat(String key, float fallbck) {
		return GenericConvert.toFloat(fetchObject(key), fallbck);
	}
	
	/**
	 * Default float fallback, To String conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted string, always possible unless null
	 **/
	default float fetchFloat(String key) {
		return GenericConvert.toFloat(fetchObject(key));
	}
	
	// to double conversion
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To double conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted string, always possible unless null
	 **/
	default double fetchDouble(String key, double fallbck) {
		return GenericConvert.toDouble(fetchObject(key), fallbck);
	}
	
	/**
	 * Default float fallback, To String conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted string, always possible unless null
	 **/
	default double fetchDouble(String key) {
		return GenericConvert.toDouble(fetchObject(key));
	}
	
	// to byte conversion
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To byte conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted string, always possible unless null
	 **/
	default byte fetchByte(String key, byte fallbck) {
		return GenericConvert.toByte(fetchObject(key), fallbck);
	}
	
	/**
	 * Default float fallback, To String conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted string, always possible unless null
	 **/
	default byte fetchByte(String key) {
		return GenericConvert.toByte(fetchObject(key));
	}
	
	// to short conversion
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To short conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted string, always possible unless null
	 **/
	default short fetchShort(String key, short fallbck) {
		return GenericConvert.toShort(fetchObject(key), fallbck);
	}
	
	/**
	 * Default short fallback, To String conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted string, always possible unless null
	 **/
	default short fetchShort(String key) {
		return GenericConvert.toShort(fetchObject(key));
	}
	
	// to UUID / GUID
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To UUID conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted UUID, always possible unless null
	 **/
	default UUID fetchUUID(String key, Object fallbck) {
		return GenericConvert.toUUID(fetchObject(key), fallbck);
	}
	
	/**
	 * Default Null fallback, To UUID conversion of generic object
	 *
	 * @param input The input value to convert
	 *
	 * @return The converted value
	 **/
	default UUID fetchUUID(String key) {
		return GenericConvert.toUUID(fetchObject(key));
	}
	
	/**
	 * To GUID conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted UUID, always possible unless null
	 **/
	default String fetchGUID(String key, Object fallbck) {
		return GenericConvert.toGUID(fetchObject(key), fallbck);
	}
	
	/**
	 * Default Null fallback, To GUID conversion of generic object
	 *
	 * @param input The input value to convert
	 *
	 * @return The converted value
	 **/
	default String fetchGUID(String key) {
		return GenericConvert.toGUID(fetchObject(key));
	}
	
	/**
	 * To List<Object> conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted Object[], always possible unless null
	 **/
	default List<Object> fetchObjectList(String key, Object fallbck) {
		return GenericConvert.toList(fetchObject(key), fallbck);
	}
	
	/**
	 * Default Null fallback, To List<Object> conversion of generic object
	 *
	 * @param input The input value to convert
	 *
	 * @default The converted value
	 **/
	default List<Object> fetchObjectList(String key) {
		return GenericConvert.toList(fetchObject(key));
	}
	
	/// To List<V> conversion of generic object
	///
	/// @param key The input value key to convert
	/// @param fallbck The fallback default (if not convertable)
	///
	/// @returns The converted Object[], always possible unless null
	default <V> List<V> fetchList(String key, Object fallbck) {
		return GenericConvert.toList(fetchObject(key), fallbck);
	}
	
	/// Default Null fallback, To List<V> conversion of generic object
	///
	/// @param input The input value to convert
	///
	/// @default The converted value
	default <V> List<V> fetchList(String key) {
		return GenericConvert.toList(fetchObject(key));
	}
	
	// to map
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To String Map conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted Map if possible, else null
	 **/
	default <K extends String, V> Map<K, V> fetchStringMap(String key, Object fallbck) {
		return GenericConvert.toStringMap(fetchObject(key), fallbck);
	}
	
	/**
	 * Default Null fallback, To String Map conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted Map if possible, else null
	 **/
	default <K extends String, V> Map<K, V> fetchStringMap(String key) {
		return GenericConvert.toStringMap(fetchObject(key));
	}
	
	/**
	 * To String Map conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted Map if possible, else null
	 **/
	default <K extends String, V> GenericConvertMap<K, V> fetchGenericConvertStringMap(String key,
		Object fallbck) {
		return GenericConvert.toGenericConvertStringMap(fetchObject(key), fallbck);
	}
	
	/**
	 * Default Null fallback, To String Map conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted Map if possible, else null
	 **/
	default <K extends String, V> GenericConvertMap<K, V> fetchGenericConvertStringMap(String key) {
		return GenericConvert.toGenericConvertStringMap(fetchObject(key));
	}
	
	// to array
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To String Map conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted Map if possible, else null
	 **/
	default GenericConvertList<V> fetchGenericConvertList(String key, Object fallbck) {
		return GenericConvert.toGenericConvertList(fetchObject(key), fallbck);
	}
	
	/**
	 * Default Null fallback, To String Map conversion of generic object
	 *
	 * @param key The input value key to convert
	 *
	 * @return The converted Map if possible, else null
	 **/
	default GenericConvertList<V> fetchGenericConvertList(String key) {
		return GenericConvert.toGenericConvertList(fetchObject(key));
	}
	
	// to string array
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To String[] conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param The fallback default (if not convertable)
	 *
	 * @return The converted String[], always possible unless null
	 **/
	default String[] fetchStringArray(String key, Object fallbck) {
		return GenericConvert.toStringArray(fetchObject(key), fallbck);
	}
	
	/**
	 * Default Null fallback, To String[] conversion of generic object
	 *
	 * @param input The input value to convert
	 *
	 * @return The converted value
	 **/
	default String[] fetchStringArray(String key) {
		return fetchStringArray(key, null);
	}
	
	// to object array
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * To Object[] conversion of generic object
	 *
	 * @param key The input value key to convert
	 * @param fallbck The fallback default (if not convertable)
	 *
	 * @return The converted Object[], always possible unless null
	 **/
	default Object[] fetchObjectArray(String key, Object fallbck) {
		return GenericConvert.toObjectArray(fetchObject(key), fallbck);
	}
	
	/**
	 * Default Null fallback, To Object[] conversion of generic object
	 *
	 * @param input The input value to convert
	 *
	 * @default The converted value
	 **/
	default Object[] fetchObjectArray(String key) {
		return GenericConvert.toObjectArray(fetchObject(key));
	}
	
	//---------------------------------------------------------------------------------------------------
	//
	//  Unmodifiable copy
	//
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * Creates an unmodifiableMap view of the current map
	 * 
	 * @return  Unmodifiable copy of the current map
	 */
	default GenericConvertMap<K, V> unmodifiableMap() {
		return ProxyGenericConvertMap.ensure(Collections.unmodifiableMap(this));
	}
	
	//---------------------------------------------------------------------------------------------------
	//
	//  FQDB Unpacking
	//
	//---------------------------------------------------------------------------------------------------
	
	// Fully Qualified Name unpacking
	//------------------------------------------------------------------------------
	
	/**
	 * This Unpacks all keynames, and rewriting any underlying map/list implementation
	 * if needed. Note that while this helps normalize input parameters against a
	 * large collection of format interpration, its implications are rarely well
	 * understood when things does not work as intended.
	 *
	 * So for example
	 * `{ "a[0].b" : "hello" }`
	 *
	 * Will unpack to
	 * ```
	 *	{
	 *		"a" : [
	 *			{
	 *				"b" : "hello"
	 *			}
	 *		]
	 *	}
	 * ```
	 **/
	// Because there is so so many ways this could go wrong, where it isn't a bug.
	// but a misunderstanding how it should work. So do test when using this.
	default void unpackFullyQualifiedNameKeys() {
		NestedObjectUtil.unpackFullyQualifiedNameKeys(this);
	}
	
}
