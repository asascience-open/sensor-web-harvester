package com.axiomalaska.sos.source

import com.axiomalaska.sos.data.Location
import com.axiomalaska.sos.data.SosStation

case class BoundingBox(southWestCorner: Location, northEastCorner: Location)

class GeoTools {
	/**
	 * Test if the station is within the bounding box setup by the North West Corner
	 * and the South West corner. 
	 * 
	 * @param station - the station to test
	 * @param southWestCorner - the South West location on the bounding box
	 * @param northEastCorner - the North East location on the bounding box
	 * @return
	 */
	def isStationWithinRegion(stationLocation:Location, 
			boundingBox:BoundingBox):Boolean = {
		if(boundingBox.northEastCorner != null && boundingBox.southWestCorner != null){
			if(doesBoundingBoxGoOver180Longitude(boundingBox)){
				return isStationWithinRegionOver180Longitude(stationLocation, 
						boundingBox);
			}
			else{
				return isStationWithinRegionNormal(stationLocation, 
						boundingBox);
			}
		}
		else{
			return true;
		}
	}
	
	// -------------------------------------------------------------------------
	// Private Members
	// -------------------------------------------------------------------------
	
	/**
	 * If the south west longitude is greater than the north east longitude, then
	 * the bounding box crosses over 180 longitude. 
	 * 
	 * @param southWestCorner - the South West location on the bounding box
	 * @param northEastCorner - the North East location on the bounding box
	 * @return
	 */
	private def doesBoundingBoxGoOver180Longitude(boundingBox:BoundingBox):Boolean ={
		
		return (boundingBox.southWestCorner.getLongitude() > boundingBox.northEastCorner.getLongitude());
	}
	
	private def isStationWithinRegionNormal(stationLocation:Location,  
			boundingBox:BoundingBox):Boolean = {
		if(stationLocation.getLatitude() <= boundingBox.northEastCorner.getLatitude() && 
		   stationLocation.getLatitude() >= boundingBox.southWestCorner.getLatitude() && 
		   stationLocation.getLongitude() <= boundingBox.northEastCorner.getLongitude() && 
		   stationLocation.getLongitude() >= boundingBox.southWestCorner.getLongitude() ){
			return true;
		}
		else{
			return false;
		}
	}
	
	private def isStationWithinRegionOver180Longitude(stationLocation:Location,
			boundingBox:BoundingBox):Boolean = {
		if (stationLocation.getLongitude() > 0) {
			if (stationLocation.getLatitude() <= boundingBox.northEastCorner.getLatitude()
				&& stationLocation.getLatitude() >= boundingBox.southWestCorner.getLatitude()
				&& stationLocation.getLongitude() > boundingBox.southWestCorner.getLongitude()) {
				return true;
			} else {
				return false;
			}
		} else {
			if (stationLocation.getLatitude() <= boundingBox.northEastCorner.getLatitude()
			   && stationLocation.getLatitude() >= boundingBox.southWestCorner.getLatitude()
			   && stationLocation.getLongitude() <= boundingBox.northEastCorner.getLongitude()) {
				return true;
			} else {
				return false;
			}
		}
	}
}