package edu.scripps.yates.shared.util;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class ProjectNamedQuery_Array_Rank_1_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  public static void deserialize(SerializationStreamReader streamReader, edu.scripps.yates.shared.util.ProjectNamedQuery[] instance) throws SerializationException {
    com.google.gwt.user.client.rpc.core.java.lang.Object_Array_CustomFieldSerializer.deserialize(streamReader, instance);
  }
  
  public static edu.scripps.yates.shared.util.ProjectNamedQuery[] instantiate(SerializationStreamReader streamReader) throws SerializationException {
    int size = streamReader.readInt();
    return new edu.scripps.yates.shared.util.ProjectNamedQuery[size];
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, edu.scripps.yates.shared.util.ProjectNamedQuery[] instance) throws SerializationException {
    com.google.gwt.user.client.rpc.core.java.lang.Object_Array_CustomFieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return edu.scripps.yates.shared.util.ProjectNamedQuery_Array_Rank_1_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    edu.scripps.yates.shared.util.ProjectNamedQuery_Array_Rank_1_FieldSerializer.deserialize(reader, (edu.scripps.yates.shared.util.ProjectNamedQuery[])object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    edu.scripps.yates.shared.util.ProjectNamedQuery_Array_Rank_1_FieldSerializer.serialize(writer, (edu.scripps.yates.shared.util.ProjectNamedQuery[])object);
  }
  
}
