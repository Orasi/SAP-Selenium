package com.orasi.core.by.angular;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.support.pagefactory.Annotations;

public class FindByNGAnnotations extends Annotations {

	private Field field;

	public FindByNGAnnotations(Field field) {
		super(field);
		this.field = field;
	}

	protected ByNG buildByFromLongFindBy(FindByNG findBy) {
		HowNG howNG = findBy.howNG();
		String using = findBy.using();

		switch (howNG) {

		case NGREPEAT:
			return ByNG.repeater(using);

		case NGMODEL:
			return ByNG.model(using);

		case NGBUTTONTEXT:
			return ByNG.buttonText(using);
		case NGCONTROLLER:
			return ByNG.controller(using);
		case NGSHOW:
			return ByNG.show(using);
		default:
			// Note that this shouldn't happen (eg, the above matches all
			// possible values for the How enum)
			throw new IllegalArgumentException(
					"Cannot determine how to locate element " + field);
		}
	}

	protected ByNG buildByFromShortFindBy(FindByNG findBy) {

		if (!"".equals(findBy.ngRepeater()))
			return ByNG.repeater(findBy.ngRepeater());
		if (!"".equals(findBy.ngModel()))
			return ByNG.model(findBy.ngModel());
		if (!"".equals(findBy.ngButtonText()))
			return ByNG.buttonText(findBy.ngButtonText());
		if (!"".equals(findBy.ngController()))
			return ByNG.controller(findBy.ngController());
		if (!"".equals(findBy.ngShow()))
			return ByNG.show(findBy.ngShow());
		// Fall through
		return null;
	}

	@SuppressWarnings("unused")
	private void assertValidFindBy(FindByNG findBy) {
		if (findBy.howNG() != null) {
			if (findBy.using() == null) {
				throw new IllegalArgumentException(
						"If you set the 'how' property, you must also set 'using'");
			}
		}

		Set<String> finders = new HashSet<String>();
		if (!"".equals(findBy.ngRepeater()))
			finders.add("ngRepeat: " + findBy.ngRepeater());
		if (!"".equals(findBy.ngModel()))
			finders.add("ngModel: " + findBy.ngModel());
		if (!"".equals(findBy.ngButtonText()))
			finders.add("ngButton: " + findBy.ngButtonText());
		if (!"".equals(findBy.ngController()))
			finders.add("ngController: " + findBy.ngController());
		if (!"".equals(findBy.ngShow()))
			finders.add("ngShow: " + findBy.ngShow());
		// A zero count is okay: it means to look by name or id.
		if (finders.size() > 1) {
			throw new IllegalArgumentException(
					String.format(
							"You must specify at most one location strategy. Number found: %d (%s)",
							finders.size(), finders.toString()));
		}
	}
}
