/**
 * Created by Xing HU on 10/27/14.
 */
public class Collection {

    private int currentSize_;       // current size of collection
    private int supportSize_;       // supported size of the array (internal use)
    private Object[] objects_;      // array of Objects (internal use)

    /**
     * Construct a Collection with default initial size
     */
    public Collection() {
        currentSize_ = 0;
        supportSize_ = 10;
        objects_ = new Object[supportSize_];
    }

    /**
     * Construct a Collection with given initial size
     * @param initialSize           Expected initial size
     */
    public Collection(int initialSize) {
        currentSize_ = 0;
        supportSize_ = initialSize;
        objects_ = new Object[supportSize_];
    }

    /**
     * Check if the Collection is empty
     * @return                      True: is empty, False: not empty
     */
    public boolean isEmpty() {
        return currentSize_ == 0;
    }

    /**
     * Make the Collection to be empty
     */
    public void makeEmpty() {
        currentSize_ = 0;
        objects_ = new Object[supportSize_];
    }

    /**
     * Insert an Object to the Collection (at the end)
     * @param obj                   The Object to be added
     * @return                      True: insert successfully, False: failed
     */
    public boolean insert(Object obj) {
        if (currentSize_ < supportSize_) {
            objects_[currentSize_] = obj;
        } else {
            // double the array size when meet the limit
            supportSize_ *= 2;
            Object[] newArray = new Object[supportSize_];
            for (int idx = 0; idx < objects_.length; ++idx)
                newArray[idx] = objects_[idx];
            objects_ = newArray;
        }

        ++currentSize_;

        return true;
    }

    /**
     * Remove Object(s) from the Collection
     * @param obj                   The Object(s) to be removed
     * @return                      True: Object removed, False: no Object removed
     */
    public boolean remove(Object obj) {
        boolean removed = false;
        if (currentSize_ > 0) {
            for (int idx = 0; idx < currentSize_; ++idx) {
                // once found, delete it and move Objects behind it to fill the hole
                if (objects_[idx].equals(obj)) {
                    objects_[idx] = null;
                    for (int i = idx + 1; i < currentSize_; ++i) {
                        objects_[i - 1] = objects_[i];
                        objects_[i] = null;
                    }
                    --currentSize_;
                    removed = true;
                }
            }
        }

        return removed;
    }

    /**
     * Check if certain Object is present in the Collection
     * @param obj                   The Object to be checked
     * @return                      True: present, False: does not present
     */
    public boolean isPresent(Object obj) {
        if (currentSize_ > 0) {
            for (int idx = 0; idx < currentSize_; ++idx)
                if (objects_[idx].equals(obj))
                    return true;
        }

        return false;
    }
}
