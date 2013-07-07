package org.laukvik.svg.shape.path;

import java.util.Vector;

import org.laukvik.svg.Coordinate;

public class PathParser {

	public static Coordinate [] listPoints( String pointsData ){
		Float [] num = listNumbers( pointsData );
		int max = num.length/2;
		Coordinate [] points = new Coordinate[ max ];
		for (int x=0; x<max; x++){
			points[ x ] = new Coordinate( num[ x*2], num[ x*2+1 ] );
		}
		return points;
	}
	
	public static Float [] listNumbers( String points ){
		String [] numberArray = points.trim().split( ",|\\s+" );
//		System.out.println( "listNumbers: " + points + " size:" + numberArray.length ); 
		Vector<Float> ps = new Vector<Float>();
		
		for (String s : numberArray){
//			System.out.println( "listNumbers: number=" + s );
			if (s.length() > 0){
				try{
					float n = Float.parseFloat( s );
					ps.add( n );
				} catch(Exception e){
					System.err.println( "Could not parse numbers: " + points ); 
					e.printStackTrace();
				}
			}

		}

		Float [] arr = new Float[ ps.size() ];
		ps.toArray( arr );
		return arr;
	}
	
	/**
	 * Parses elements
	 * <br>
	 * e.g.
	 * <br>
	 * M593248,62550 l-85,27 l-815,-27 l-2275,1980 l-2068,392 l-915,-87 l-1006,-28 l-1691,-305 l-1494,-90 
	 * l-176,18 l-333,-755 l-2370,147 l-840,120 l508,1105 l-110,940 l-1515,615 l125,2528 l-4703,1027 
	 * l-2310,-705 l-925,-210 z
	 * 
	 * @param pathData
	 * @return
	 */
	public static Vector <PathElement> parsePathElements( String pathData ){
		Vector <PathElement> items = new Vector<PathElement>(); 
		
		/* Create a string with formatted like a pairs command=data */
		String dataEqualsData = pathData.trim().replaceAll( "(m|M|z|Z|l|L|h|H|v|V|c|C|s|S|q|Q|t|T|a|A)", "\\\n$0=" ).trim();

		/* Split string into arrayof pairs */
		String [] pathElements = dataEqualsData.split( "\n" );
		for (String p : pathElements){
			String [] keyValue = p.split( "=" );

			if (p.startsWith( Close.RELATIVE )){

				items.add( new Close() );
				
			} else if (keyValue.length == 2){
				
				String command = keyValue[ 0 ];
				String values = keyValue[ 1 ];
				boolean absolute = command.charAt( 0 ) < ('a');
				
				if (command.equalsIgnoreCase( MoveTo.RELATIVE )){

//					log( "MoveTo: " + values );
					MoveTo moveTo = new MoveTo( values );
					if (moveTo != null){
						moveTo.absolute = absolute;
						items.add( moveTo );
					}					
					
				} else if (command.equalsIgnoreCase( CubicCurveTo.RELATIVE ) || command.equalsIgnoreCase( CubicCurveTo.RELATIVE_SMOOTH )){
					
					/* CubicCurveTo */
//					CubicCurveTo cubicCurveTo = new CubicCurveTo( values );
//					cubicCurveTo.absolute = absolute;
//					cubicCurveTo.smooth = command.equalsIgnoreCase( CubicCurveTo.RELATIVE_SMOOTH );
//					items.add( cubicCurveTo );
					
					Coordinate [] points = listPoints( values );
					if (points.length == 3){
						Coordinate p1 = points[ 0 ];
						Coordinate p2 = points[ 1 ];
						Coordinate p3 = points[ 2 ];
						CubicCurveTo cubicCurveTo = new CubicCurveTo( p1.x, p1.y, p2.x, p2.y, p3.x, p3.y );
						cubicCurveTo.absolute = absolute;
						items.add( cubicCurveTo );
						
					} else if (points.length == 2){
						Coordinate p1 = points[ 0 ];
						Coordinate p2 = points[ 1 ];
						CubicCurveTo cubicCurveTo = new CubicCurveTo( p1.x, p1.y, p2.x, p2.y, p1.x, p1.y );
						cubicCurveTo.absolute = absolute;
						items.add( cubicCurveTo );
					}
					
				} else if (command.equalsIgnoreCase( EllipseCurveTo.RELATIVE )){
					
					/* EllipseCurveTo */
//					log( "EllipseCurveTo: " + values );
					Coordinate [] points = listPoints( values );

					Coordinate p1 = points[ 0 ];
					Coordinate p2 = points[ 1 ];
					Coordinate p3 = points[ 2 ];
					EllipseCurveTo elipseCurveTo = new EllipseCurveTo( p1.x, p1.y, p2.x, p2.y, p3.x, p3.y );
					elipseCurveTo.absolute = absolute;
					items.add( elipseCurveTo );

				} else if (command.equalsIgnoreCase( QuadCurveTo.RELATIVE ) || command.equalsIgnoreCase( QuadCurveTo.RELATIVE_SMOOTH )){
					
					/* QuadCurveTo */
//					log( "QuadCurveTo: " + values );
					Coordinate [] points = listPoints( values );
					Coordinate p1 = points[ 0 ];
					Coordinate p2 = points[ 1 ];
					QuadCurveTo quadCurveTo = new QuadCurveTo( p1.x, p1.y, p2.x, p2.y );
					quadCurveTo.absolute = absolute;
					items.add( quadCurveTo );

				} else if (command.equalsIgnoreCase( LineTo.RELATIVE )){
					
//					log( "LineTo: " + values );
					/* Lineto */
					Coordinate [] points = listPoints( values );
					/* Add each point */
					for (Coordinate p2 : points){
//						log( "\t" + p2.x + "x" + p2.y );
						LineTo lineTo = new LineTo( p2.x, p2.y );
						lineTo.absolute = command.startsWith( LineTo.ABSOLUTE );
						items.add( lineTo );
					}
					
				} else {
//					log( "Unknown command: " + command );
				}
			} else {
//				log( "Not enough data: " + pathData );
			}
			
		}
		return items;
	}

	public static void main( String [] args ){
		System.out.println( PathParser.listNumbers("0.13037-0.04102,0.21191-0.15137,0.18164-0.24561") );
	}
	
}