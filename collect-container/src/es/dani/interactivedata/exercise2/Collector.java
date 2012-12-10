package es.dani.interactivedata.exercise2;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

public class Collector {

	/**
	 * Method that collects all the objects of the object type passed as
	 * parameter contained in the container parameter
	 * 
	 * @param container
	 *            the container with the components to be collected
	 * @param object
	 *            indicates the type of the components to be collected
	 * @return List<Component> a List with all the collected components, an
	 *         empty List if no component is found. It returns an empty List in
	 *         case one of the passed parameters is set to null
	 */
	public List<Component> collectComponents(final Container container,
			final Component object) {

		List<Component> componentList = new ArrayList<Component>();
		/* objects will be collected if both parameters are not null */
		if (container != null && object != null) {
			/* iteration through all the components of the container */
			Component[] components = container.getComponents();
			for (Component component : components) {
				/*
				 * if one component is a container all components inside this
				 * sub container (recursion) will be added to the result list
				 */
				if (component.getClass().isInstance(container)) {
					componentList.addAll(collectComponents(
							(Container) component, object));
				}
				/*
				 * if one component is an instance of the desired class it is
				 * added to the result component list
				 */
				if (object.getClass().isInstance(component)) {
					componentList.add(component);
				}
			}
		}
		/* returns the component list */
		return componentList;
	}
}
