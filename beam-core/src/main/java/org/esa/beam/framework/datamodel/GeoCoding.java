/*
 * $Id: GeoCoding.java,v 1.1.1.1 2006/09/11 08:16:45 norman Exp $
 *
 * Copyright (C) 2002 by Brockmann Consult (info@brockmann-consult.de)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation. This program is distributed in the hope it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.esa.beam.framework.datamodel;

import org.esa.beam.framework.dataop.maptransf.Datum;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

import java.awt.geom.AffineTransform;


/**
 * The <code>GeoCoding</code> interface provides geo-spatial latitude and longitude information for a given X/Y position
 * of any (two-dimensional) raster.
 * <p> <b> Note: New geo-coding implementations shall implement the abstract class {@link AbstractGeoCoding},
 * instead of implementing this interface.</b>
 * </p>
 * <p/>
 * <p>All <code>GeoCoding</code> implementations should override
 * the {@link Object#equals(Object) equals()} and  {@link Object#hashCode() hashCode()} methods.</p>
 *
 * @author Norman Fomferra
 * @version $Revision$ $Date$
 */
public interface GeoCoding {

    /**
     * Checks whether or not the longitudes of this geo-coding cross the +/- 180 degree meridian.
     *
     * @return <code>true</code>, if so
     */
    boolean isCrossingMeridianAt180();

    /**
     * Checks whether or not this geo-coding can determine the pixel position from a geodetic position.
     *
     * @return <code>true</code>, if so
     */
    boolean canGetPixelPos();

    /**
     * Checks whether or not this geo-coding can determine the geodetic position from a pixel position.
     *
     * @return <code>true</code>, if so
     */
    boolean canGetGeoPos();

    /**
     * Returns the pixel co-ordinates as x/y for a given geographical position given as lat/lon.
     *
     * @param geoPos   the geographical position as lat/lon in the coodinate system determined by {@link #getDatum()}
     * @param pixelPos an instance of <code>Point</code> to be used as retun value. If this parameter is
     *                 <code>null</code>, the method creates a new instance which it then returns.
     *
     * @return the pixel co-ordinates as x/y
     */
    PixelPos getPixelPos(final GeoPos geoPos, PixelPos pixelPos);

    /**
     * Returns the latitude and longitude value for a given pixel co-ordinate.
     *
     * @param pixelPos the pixel's co-ordinates given as x,y
     * @param geoPos   an instance of <code>GeoPos</code> to be used as retun value. If this parameter is
     *                 <code>null</code>, the method creates a new instance which it then returns.
     *
     * @return the geographical position as lat/lon in the coodinate system determined by {@link #getDatum()}
     */
    GeoPos getGeoPos(final PixelPos pixelPos, GeoPos geoPos);

    /**
     * Gets the datum, the reference point or surface against which {@link GeoPos} measurements are made.
     *
     * @return the datum
     */
    Datum getDatum();

    /**
     * Releases all of the resources used by this object instance and all of its owned children. Its primary use is to
     * allow the garbage collector to perform a vanilla job.
     * <p/>
     * <p>This method should be called only if it is for sure that this object instance will never be used again. The
     * results of referencing an instance of this class after a call to <code>dispose()</code> are undefined.
     */
    void dispose();

    /**
     * @return The image coordinate reference system (CRS). It is usually derived from the base CRS by including
     *         a linear or non-linear transformation from base (geodetic) coordinates to image coordinates.
     */
    CoordinateReferenceSystem getImageCRS();

    /**
     * @return The map coordinate reference system (CRS). It may be either a geographical CRS (nominal case is
     *         "WGS-84") or a derived projected CRS, e.g. "UTM 32 - North".
     */
    CoordinateReferenceSystem getMapCRS();

    /**
     * @return The geographical coordinate reference system (CRS). It may be either "WGS-84" (nominal case) or
     *         any other geographical CRS.
     */
    CoordinateReferenceSystem getGeoCRS();

    /**
     * @return The math transformation used to convert image coordinates to map coordinates.
     */
    MathTransform getImageToMapTransform();

    /**
     * @return The base coordinate reference system (CRS). It may be either a geographical CRS (nominal case is
     *         "WGS-84") or a derived projected CRS, e.g. "UTM 32 - North".
     *
     * @deprecated since BEAM 4.7, use {@link #getMapCRS()} instead.
     */
    @Deprecated
    CoordinateReferenceSystem getBaseCRS();

    /**
     * @return The model coordinate reference system (CRS). It may be the same as the base CRS for rectified,
     *         geo-referenced images or may be same as the image CRS for unrectified images still in satellite coordinates.
     *
     * @deprecated since BEAM 4.7, use {@link org.esa.beam.jai.ImageManager#getModelCrs(GeoCoding)} instead.
     */
    @Deprecated
    CoordinateReferenceSystem getModelCRS();

    /**
     * @return The affine transformation used to convert image coordinates to model coordinates.
     *
     * @deprecated since BEAM 4.7, use {@link org.esa.beam.jai.ImageManager#getImageToModelTransform(GeoCoding)} instead.
     */
    @Deprecated
    AffineTransform getImageToModelTransform();

}
