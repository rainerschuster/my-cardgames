package com.rainerschuster.cardgames.client.dnd;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.allen_sauer.gwt.dnd.client.DragHandler;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Rainer Schuster
 */
public final class DNDManager {

	private static final Logger LOG = Logger.getLogger(DNDManager.class.getName());

	private static List<DropController> dropControllers = new ArrayList<DropController>();
	private static List<DragHandler> dragHandlers = new ArrayList<DragHandler>();

	private static PickupDragController dragController = null;

//	public static PickupDragController getDragController() {
//		return dragController;
//	}

	public static void setDragController(PickupDragController dragController) {
		DNDManager.dragController = dragController;
	}

	public static void unregisterAll() {
		LOG.log(Level.INFO, "unregisterAll"); //$NON-NLS-1$
		for (DragHandler dragHandler : dragHandlers) {
			dragController.removeDragHandler(dragHandler);
		}
		dragHandlers.clear();
		unregisterDropControllers();
	}

	public static void registerDropController(final DropController dropController) {
		LOG.log(Level.INFO, "registerDropController"); //$NON-NLS-1$
		dragController.registerDropController(dropController);
		dropControllers.add(dropController);
	}

	public static void unregisterDropController(final DropController dropController) {
		LOG.log(Level.INFO, "unregisterDropController"); //$NON-NLS-1$
		dragController.unregisterDropController(dropController);
		dropControllers.remove(dropController);
	}

	public static void unregisterDropControllers() {
		LOG.log(Level.INFO, "unregisterDropControllers"); //$NON-NLS-1$
		dragController.unregisterDropControllers();
		dropControllers.clear();
	}

	public static void addDragHandler(final DragHandler handler) {
		LOG.log(Level.INFO, "addDragHandler"); //$NON-NLS-1$
		dragController.addDragHandler(handler);
		dragHandlers.add(handler);
	}
	public static void removeDragHandler(final DragHandler handler) {
		LOG.log(Level.INFO, "removeDragHandler"); //$NON-NLS-1$
		dragController.removeDragHandler(handler);
		dragHandlers.remove(handler);
	}
	public static void makeDraggable(final Widget draggable) {
		LOG.log(Level.INFO, "makeDraggable"); //$NON-NLS-1$
		dragController.makeDraggable(draggable);
	}
	public static void makeDraggable(final Widget draggable, final Widget dragHandle) {
		LOG.log(Level.INFO, "makeDraggable"); //$NON-NLS-1$
		dragController.makeDraggable(draggable, dragHandle);
	}
	public static void makeNotDraggable(final Widget draggable) {
		LOG.log(Level.INFO, "makeNotDraggable"); //$NON-NLS-1$
		dragController.makeNotDraggable(draggable);
	}

	public static boolean isDropTarget(final Widget widget) {
		LOG.log(Level.INFO, "isDropTarget"); //$NON-NLS-1$
		for (DropController dropController : dropControllers) {
			if (dropController.getDropTarget() == widget) {
				return true;
			}
		}
		return false;
	}

	public static DropController getDropTarget(final Widget widget) {
		LOG.log(Level.INFO, "getDropTarget"); //$NON-NLS-1$
		for (DropController dropController : dropControllers) {
			if (dropController.getDropTarget() == widget) {
				return dropController;
			}
		}
		return null;
	}

}
