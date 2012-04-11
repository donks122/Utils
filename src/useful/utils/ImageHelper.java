package useful.utils;

import android.widget.ImageView;
//A class helper to find out if the touch was on the image, and neglect if outside the image
public class ImageHelper {
  
  public static boolean hitsImage( ImageView _iv, float x, float y ) {
    float _width = _iv.getWidth();
    float _height = _iv.getHeight();
    float _iwidth = _iv.getDrawable().getIntrinsicWidth();
    float _iheight = _iv.getDrawable().getIntrinsicHeight();
    
    float _actual_ratio = _iwidth / _iheight;
    float _view_ratio = _width / _height;
    
    float _x1, _y1, _w, _h;
    
    if( _view_ratio > _actual_ratio ) {
      _w = _height * _actual_ratio;
      _h = _height;
    } else {
      _w = _width;
      _h = _width / _actual_ratio;
    }
    
    _x1 = ( _w == _width ) ? 0 : Math.abs( ( _w - _width) / 2 );
    _y1 = ( _h == _height ) ? 0 : Math.abs( ( _h - _height ) /2 );

    
    if( x >= _x1 && x <= ( _x1 + _w ) && y >= _y1 && y <= ( _y1 + _h )) {
      return true;
    }
    
    return false;

  }

}
