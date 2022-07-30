package placeholdername.content;

import arc.func.Prov;
import arc.struct.ObjectIntMap;
import arc.struct.ObjectMap.Entry;
import mindustry.gen.*;
import mindustry.type.unit.ErekirUnitType;
import placeholdername.types.CloakUnit;

public class CloakUnits {

    private static Entry<Class<? extends Entityc>, Prov<? extends Entityc>>[] types = new Entry[]{
            prov(CloakUnit.class, CloakUnit::new)
    };

    public static ObjectIntMap<Class<? extends Entityc>> idMap = new ObjectIntMap<>();

    private static <T extends Entityc> Entry<Class<T>, Prov<T>> prov(Class<T> type, Prov<T> prov) {
        Entry<Class<T>, Prov<T>> entry = new Entry<>();
        entry.key = type;
        entry.value = prov;
        return entry;
    }

    private static void setupID() {
        int start = 33;

        int[] free = new int[types.length];
        for (int i = start, j = 0; i < EntityMapping.idMap.length; i++) {
            if (EntityMapping.idMap[i] == null) free[j++] = i;
            if (j > free.length - 1) break;
        }

        for (int i = 0; i < free.length; i++) {
            idMap.put(types[i].key, free[i]);
            EntityMapping.idMap[free[i]] = types[i].value;
        }
    }

    public static <T extends Entityc> int classID(Class<T> type) {
        return idMap.get(type, -1);
    }

    public static void load() {
        setupID();
        new ErekirUnitType("diminish") {{
            constructor = CloakUnit::create;
        }};
    }
}