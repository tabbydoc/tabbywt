package webreduce.data;

import java.io.Serializable;

public enum TableType implements Serializable {
	LAYOUT, // для оформления (ненужная херня)
	RELATION, // односторонние
	MATRIX, // многосторонние
	ENTITY, // односторонние
	OTHER // ненужные с непонятной структурой
}
