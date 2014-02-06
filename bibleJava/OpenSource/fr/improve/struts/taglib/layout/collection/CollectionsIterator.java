package fr.improve.struts.taglib.layout.collection;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import fr.improve.struts.taglib.layout.util.LayoutUtils;

/**
 * A kind of iterator that can iterates on nested collections.
 * 
 * @author jer80876
 */
public class CollectionsIterator {
	private Iterator mainIterator;
	private String id;
	private String indexId;
	private String property;
	private int index = 0;
	
	private CollectionsIterator nestedIterator;

	/**
	 * Build a new CollectionsIterator.
	 */	
	public CollectionsIterator(Object in_bean, String in_property, String in_id, String in_indexId) throws JspException {
		Object lc_collection = in_bean;
		if (in_bean!=null) {
			// mainIterator is lazy instanciated for subcollections
			lc_collection = LayoutUtils.getProperty(in_bean, in_property);
			mainIterator = LayoutUtils.getCollection(lc_collection).iterator();
		}
		id = in_id;
		property = in_property;
		indexId = in_indexId;
	}
	
	/**
	 * Add a nested collection.
	 */
	public void addLastIterator(CollectionsIterator in_iterator) {
		if (nestedIterator==null) {
			nestedIterator  = in_iterator;	
		} else {
			nestedIterator.addLastIterator(in_iterator);
		}
	}
		
	/**
	 * Return true if one of the iterators has a next element.
	 */
	public boolean hasNext(PageContext in_pageContext) {
		if ((mainIterator!=null && mainIterator.hasNext()) || (nestedIterator!=null && nestedIterator.hasNext(in_pageContext))) {
			return true;
		} else {
			if (id!=null) {
				in_pageContext.removeAttribute(id, PageContext.PAGE_SCOPE);
			}
			if (indexId!=null) {
				in_pageContext.removeAttribute(indexId, PageContext.PAGE_SCOPE);
			}
			return false;
		}
	}
	
	/**
	 * Return true if the iterator is initialized.
	 */
	private boolean isInitialized() {
		return mainIterator != null;	
	}
	
	/**
	 * Return true if there is a nested iterator.
	 */
	protected boolean hasNestedIterator() {
		return nestedIterator!=null;	
	}

	/**
	 * Get the next value(s) to iterator on.
	 * out_changes is updated and contains the span of the element that have changed.
	 */
	public Object next(PageContext in_pageContext, Map out_changes) throws JspException {
		return next(in_pageContext, out_changes, true);
	}
	
	private Object next(PageContext in_pageContext, Map out_changes, boolean in_increment) throws JspException {
		if (nestedIterator==null) {
			Object lc_bean = mainIterator.next();
			if (id!=null) {
				in_pageContext.setAttribute(id, lc_bean);
				out_changes.put(id, new Integer(1));
			}
			if (indexId!=null) {
				in_pageContext.setAttribute(indexId, new Integer(index));
			}
			if (in_increment) {
				index++;
			}
			return lc_bean;
		} else {
			if (nestedIterator.hasNext(in_pageContext)) {
				nestedIterator.next(in_pageContext, out_changes, in_increment);
				return null;
			} else {
				Object lc_bean = mainIterator.next();
				int lc_span = 1;
				lc_span = nestedIterator.reset(in_pageContext, lc_bean);
				
				if (nestedIterator.hasNext(in_pageContext)) {
					nestedIterator.next(in_pageContext, out_changes, in_increment);
				} else {
					nestedIterator.emptyNext(in_pageContext, out_changes);
				}
				
				if (id!=null) {
					in_pageContext.setAttribute(id, lc_bean);
					out_changes.put(id, new Integer(lc_span));
				}
				if (indexId!=null) {
					in_pageContext.setAttribute(indexId, new Integer(index));
				}
				if (in_increment) {
					index++;
				}
				return lc_bean;			
			}
			
		} 
	}
	
	/**
	 * Skip an element. Used by the pagination and offset system.
	 */
	public void skip(PageContext in_pageContext, Map out_changes) throws JspException {
		if (nestedIterator==null) {
			// No nested iterator : easy !
			next(in_pageContext, out_changes, false);
		} else {
			// finish the nested iterator.
			while (nestedIterator.hasNext(in_pageContext)) {
				nestedIterator.next(in_pageContext, out_changes, false);
				out_changes.clear();
			}
			// next main element.
			next(in_pageContext, out_changes, false);
			// finish the nested iterator for the next element.
			while (nestedIterator.hasNext(in_pageContext)) {
				nestedIterator.next(in_pageContext, out_changes, false);
				out_changes.clear();
			}
		}
	}
	
	/**
	 * Effectue une itétation vide...
	 */
	private void emptyNext(PageContext in_pg, Map out_changes) {
		if (nestedIterator==null) {
			if (id!=null) {
				in_pg.removeAttribute(id, PageContext.PAGE_SCOPE);
				out_changes.put(id, new Integer(1));
			}
		} else {
			throw new IllegalStateException("Case not implemented");	
		}
	}
	
	/**
	 * Reinitialize this iterator with a new bean.
	 */
	private int reset(PageContext in_pg, Object in_bean) throws JspException {
		Collection lc_collection = LayoutUtils.getCollection(LayoutUtils.getProperty(in_bean, property));
		if (lc_collection==null) {
			// deal with null nested collections.
			lc_collection = Collections.EMPTY_LIST;
		}
		mainIterator = 	lc_collection.iterator();
		index = 0;
		if (indexId!=null) {
			in_pg.removeAttribute(indexId, PageContext.PAGE_SCOPE);
		}		
		if (nestedIterator==null) {
			return lc_collection.size();
		} else {
			// need to pre-compute the length of the nested iterator so that we can generate correct rowspan values...
			return nestedIterator.computeLength(lc_collection);
		}
	}
	
	/**
	 * Compute the length of this subiterator applied to the specified collection.
	 */
	private int computeLength(Collection in_collection) throws JspException {
		Iterator lc_it = in_collection.iterator();
		int lc_length = 0;
		while (lc_it.hasNext()) {
			Object lc_bean = lc_it.next();
			Collection lc_collection = LayoutUtils.getCollection(LayoutUtils.getProperty(lc_bean, property));
			if (nestedIterator==null) {
				int lc_size = lc_collection.size();
				lc_length += lc_size > 1 ? lc_size : 1;
			} else {
				lc_length += nestedIterator.computeLength(lc_collection);
			}
		}
		return lc_length;
	}
	
	public int getIndex() {
		return index;
	}
	
	protected CollectionsIterator getNestedIterator() {
		return nestedIterator;
	}
}
