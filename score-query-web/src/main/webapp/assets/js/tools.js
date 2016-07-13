/**
 * Created by TimeLiar on 16/6/12.
 *
 */

Toast = {};
IconMap = {};
IconMap['success'] = 'glyphicon glyphicon-ok';
IconMap['info'] = 'glyphicon glyphicon-info-sign';
IconMap['warning'] = 'glyphicon glyphicon-warning-sign';
IconMap['danger'] = 'glyphicon glyphicon-remove';
Toast.show = function (content) {
    var contentObj = {
        content: {
            message: typeof content === 'object' ? content.message : content,
            type: typeof content === 'object' ? (content.type || 'info') : 'info',
            title: content.title || '',
            icon: content.type ? IconMap[content.type] : IconMap['info'],
            url: content.url || '#',
            target: content.target || '-',
            delay: content.delay || 100,
            callback: content.callback || null
        }
    };
    $.notify({
        icon: contentObj.content.icon,
        title: contentObj.content.title,
        message: contentObj.content.message
    }, {
        type: contentObj.content.type,
        delay: contentObj.content.delay,
        placement: {
            from: "bottom",
            align: "right"
        },
        animate: {
            enter: 'animated bounceIn',
            exit: 'animated bounceOut'
        },
        offset: {
            y: 40,
            x: 20
        },
        z_index: 999999,
        onClose: contentObj.content.callback
    });
};
