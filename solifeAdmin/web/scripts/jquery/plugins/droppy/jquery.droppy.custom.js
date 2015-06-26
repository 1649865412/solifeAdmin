/*
 * Droppy 0.1.2
 * (c) 2008 Jason Frame (jason@onehackoranother.com)
 */
$j.fn.droppy = function(options) {
    
  options = $j.extend({speed: 250}, options || {});
  
  this.each(function() {
    
    var root = this, zIndex = 500;
    
    function getSubnav(ele) {
      if (ele.nodeName.toLowerCase() == 'li') {
        var subnav = $j('> ul', ele);
        return subnav.length ? subnav[0] : null;
      } else {
        return ele;
      }
    }
    
    function getActuator(ele) {
      if (ele.nodeName.toLowerCase() == 'ul') {
        return $j(ele).parents('li')[0];
      } else {
        return ele;
      }
    }
    
    function hide() {
      var subnav = getSubnav(this);
      if (!subnav) return;
      $j.data(subnav, 'cancelHide', false);
      setTimeout(function() {
        if (!$j.data(subnav, 'cancelHide')) {
          $j(subnav).hide();
        }
      }, 200);
    }
  
    function show() {
      var subnav = getSubnav(this);
      if (!subnav) return;
      $j.data(subnav, 'cancelHide', true);
      $j(subnav).css({zIndex: zIndex++}).show();
      if (this.nodeName.toLowerCase() == 'ul') {
        var li = getActuator(this);
        $j(li).addClass('hover');
        $j('> a', li).addClass('hover');
      }
    }
    
    
    
    $j('ul:not(.noHover),li:not(.noHover)', this).hover(show, hide);
    $j('.noHover').click(show);
    $j('.noHover').bind('mouseleave',hide);
    //if $j('.noHover').toggle(show,show) then the menu will not hide after the mouse click
    
    $j('li', this).hover(
      function() { $j(this).addClass('hover'); $j('> a', this).addClass('hover'); },
      function() { $j(this).removeClass('hover'); $j('> a', this).removeClass('hover'); }
    );
    
  });
  
  
};
