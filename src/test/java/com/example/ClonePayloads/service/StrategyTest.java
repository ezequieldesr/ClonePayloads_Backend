package com.example.ClonePayloads.service;
import com.example.ClonePayloads.dto.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
class StrategyTest {
    PayloadService service=new PayloadService();
    FieldRole rule(String field,String strategy){var r=new FieldRole();r.setField(field);r.setStrategy(strategy);return r;}
    @Test void timestampIncrementsPerClone(){var req=new PayloadRequest(3,Map.of("timestamp","2026-01-01T00:00:00Z"),null);var result=service.duplicate(req).getPayloads();assertEquals("2026-01-01T00:00:00.001Z",result.get(0).get("timestamp"));assertEquals("2026-01-01T00:00:00.003Z",result.get(2).get("timestamp"));}
    @Test void numericPreservesDecimals(){var result=service.duplicate(new PayloadRequest(2,Map.of("price",new BigDecimal("10.25")),List.of(rule("price","NUMERIC_INCREMENT")))).getPayloads();assertEquals(new BigDecimal("11.25"),result.get(0).get("price"));}
    @Test void uuidIsUnique(){var out=service.duplicate(new PayloadRequest(3,Map.of("id","base"),List.of(rule("id","UUID")))).getPayloads();assertEquals(3,out.stream().map(m->UUID.fromString((String)m.get("id"))).distinct().count());}
    @Test void fixedValuesRotate(){var r=rule("state","FIXED_VALUES");r.setValues(List.of("A","B"));var out=service.duplicate(new PayloadRequest(3,Map.of("state","base"),List.of(r))).getPayloads();assertEquals(List.of("A","B","A"),out.stream().map(m->m.get("state")).toList());}
    @Test void explicitNoRulesPreservesPayload(){var payload=Map.<String,Object>of("name","Marina");var out=service.duplicate(new PayloadRequest(2,payload,List.of())).getPayloads();assertEquals(payload,out.getFirst());assertNotSame(payload,out.getFirst());}
    @Test void missingFieldIsRejected(){assertThrows(IllegalArgumentException.class,()->service.generatorPayload(Map.of("id","base"),List.of(rule("absent","UUID")),1));}
    @Test void emptyFixedValuesIsRejected(){assertThrows(IllegalArgumentException.class,()->service.generatorPayload(Map.of("id","base"),List.of(rule("id","FIXED_VALUES")),1));}
}
