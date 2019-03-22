// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import com.alu.motive.hal.commons.dto.ParameterDTO;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.apache.spark.api.java.Optional;
import java.util.AbstractMap;
import com.alu.hal.streaming.utils.ParamUtils;
import com.alu.hal.streaming.process.TransposeTableRowFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.apache.log4j.Logger;

public class SpecialParametersCreator
{
    private static Logger LOG;
    private static final String ASSOC_DEVICE_MAC_ADDRESS = "WiFiAssocDevice_AssociatedDeviceMACAddress";
    private static final String ASSOC_DEVICE_HOSTNAME = "WiFiAssocDevice_X_M_HostName";
    private static final String HOSTS_MAC_ADDRESS = "Hosts_MACAddress";
    private static final String HOSTS_HOSTNAME = "Hosts_HostName";
    
    public static void create(final List<DecoratedParameter> decoratedParameters, final ModelMetadata modelMetadata) {
        final List<DecoratedParameter> macAddresses = findParameter(decoratedParameters, "Hosts_MACAddress");
        final List<DecoratedParameter> hostNames = findParameter(decoratedParameters, "Hosts_HostName");
        final Map<String, String> macToHost = createMACToHostname(macAddresses, hostNames);
        decoratedParameters.removeAll(macAddresses);
        decoratedParameters.removeAll(hostNames);
        addSpecialParameters(decoratedParameters, modelMetadata, macToHost);
    }
    
    private static Map<String, String> createMACToHostname(final List<DecoratedParameter> macAddresses, final List<DecoratedParameter> hostNames) {
        final Map<TransposeTableRowFactory.IndexPath, Map.Entry<String, String>> macToHostPerIndex = new HashMap<TransposeTableRowFactory.IndexPath, Map.Entry<String, String>>();
        final List<String> indexes;
        final TransposeTableRowFactory.IndexPath indexPath;
        final Map<TransposeTableRowFactory.IndexPath, AbstractMap.SimpleEntry<String, Object>> map;
        macAddresses.forEach(macAddr -> {
            indexes = ParamUtils.getIndexes(macAddr.getParameterDTO().getName(), macAddr.getParamMetaData().getCompiledParamWithPathPattern());
            indexPath = TransposeTableRowFactory.IndexPath.create(indexes);
            map.put(indexPath, new AbstractMap.SimpleEntry<String, Object>((String)macAddr.getValue(), null));
            return;
        });
        final List<String> indexes2;
        final TransposeTableRowFactory.IndexPath indexPath2;
        final Map<K, Map.Entry<K, String>> map2;
        hostNames.forEach(hostName -> {
            indexes2 = ParamUtils.getIndexes(hostName.getParameterDTO().getName(), hostName.getParamMetaData().getCompiledParamWithPathPattern());
            indexPath2 = TransposeTableRowFactory.IndexPath.create(indexes2);
            if (map2.containsKey(indexPath2)) {
                map2.get(indexPath2).setValue((String)hostName.getValue());
            }
            return;
        });
        final Map<String, String> macToHost = new HashMap<String, String>();
        final Map<Object, V> map3;
        macToHostPerIndex.values().forEach(macEntry -> {
            if (!map3.containsKey(macEntry.getKey())) {
                map3.put(macEntry.getKey(), macEntry.getValue());
            }
            return;
        });
        return macToHost;
    }
    
    private static void addSpecialParameters(final List<DecoratedParameter> decoratedParameters, final ModelMetadata modelMetadata, final Map<String, String> macToHost) {
        final List<DecoratedParameter> assocMACs = findParameter(decoratedParameters, "WiFiAssocDevice_AssociatedDeviceMACAddress");
        final Optional<ParamMetaData> hostnameMetadata = (Optional<ParamMetaData>)Optional.fromNullable((Object)modelMetadata.getParamMetaDataList().stream().filter(param -> param.getTransposeParamColumnMapping().getTransposeValueColumn().getName().equals("WiFiAssocDevice_X_M_HostName")).findFirst().orElse(null));
        SpecialParametersCreator.LOG.debug("hostnameMetadata: " + hostnameMetadata);
        if (hostnameMetadata.isPresent()) {
            final List<String> indexes;
            final Optional optional;
            final Optional<DecoratedParameter> assocHostnameParam;
            assocMACs.forEach(assocMAC -> {
                indexes = ParamUtils.getIndexes(assocMAC.getParameterDTO().getName(), assocMAC.getParamMetaData().getCompiledParamWithPathPattern());
                assocHostnameParam = createParameter(indexes, (ParamMetaData)optional.get(), macToHost.get(assocMAC.getValue()));
                if (assocHostnameParam.isPresent()) {
                    decoratedParameters.add((DecoratedParameter)assocHostnameParam.get());
                }
            });
        }
    }
    
    private static List<DecoratedParameter> findParameter(final List<DecoratedParameter> decoratedParameters, final String parameterName) {
        return decoratedParameters.stream().filter(decoratedParameter -> decoratedParameter.getParamMetaData().getTransposeParamColumnMapping().getTransposeValueColumn().getName().equalsIgnoreCase(parameterName)).collect((Collector<? super Object, ?, List<DecoratedParameter>>)Collectors.toList());
    }
    
    private static Optional<DecoratedParameter> createParameter(final List<String> indexes, final ParamMetaData hostnameMetadata, final String hostName) {
        final Optional<String> parameterName = buildParameterName(indexes, hostnameMetadata);
        if (!parameterName.isPresent()) {
            return (Optional<DecoratedParameter>)Optional.absent();
        }
        return (Optional<DecoratedParameter>)Optional.of((Object)new DecoratedParameter(new ParameterDTO((String)parameterName.get(), hostName), hostnameMetadata));
    }
    
    private static Optional<String> buildParameterName(final List<String> indexes, final ParamMetaData hostnameMetadata) {
        final String[] paramParts = hostnameMetadata.getCompiledParamWithPathPattern().pattern().split("\\(\\[0\\-9\\]\\+\\)");
        if (paramParts.length != 1 + indexes.size()) {
            SpecialParametersCreator.LOG.error("Retrieved parameter pattern (" + hostnameMetadata.getCompiledParamWithPathPattern().pattern() + ") does not match the indexes (" + indexes + ") - will be ignored.");
            return (Optional<String>)Optional.absent();
        }
        String parameterName = "";
        for (int i = 0; i < indexes.size(); ++i) {
            parameterName = parameterName + paramParts[i] + indexes.get(i);
        }
        parameterName += paramParts[paramParts.length - 1];
        return (Optional<String>)Optional.of((Object)parameterName);
    }
    
    static {
        SpecialParametersCreator.LOG = Logger.getLogger(SpecialParametersCreator.class);
    }
}
