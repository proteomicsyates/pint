package org.reactome.web.pwp.model.util;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class LruCache_Array_Rank_1_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  public static void deserialize(SerializationStreamReader streamReader, org.reactome.web.pwp.model.util.LruCache[] instance) throws SerializationException {
    com.google.gwt.user.client.rpc.core.java.lang.Object_Array_CustomFieldSerializer.deserialize(streamReader, instance);
  }
  
  public static org.reactome.web.pwp.model.util.LruCache[] instantiate(SerializationStreamReader streamReader) throws SerializationException {
    int size = streamReader.readInt();
    return new org.reactome.web.pwp.model.util.LruCache[size];
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, org.reactome.web.pwp.model.util.LruCache[] instance) throws SerializationException {
    com.google.gwt.user.client.rpc.core.java.lang.Object_Array_CustomFieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return org.reactome.web.pwp.model.util.LruCache_Array_Rank_1_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    org.reactome.web.pwp.model.util.LruCache_Array_Rank_1_FieldSerializer.deserialize(reader, (org.reactome.web.pwp.model.util.LruCache[])object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    org.reactome.web.pwp.model.util.LruCache_Array_Rank_1_FieldSerializer.serialize(writer, (org.reactome.web.pwp.model.util.LruCache[])object);
  }
  
}
