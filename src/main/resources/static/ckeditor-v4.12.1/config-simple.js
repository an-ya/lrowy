/**
 * 简单版配置
 */

CKEDITOR.editorConfigCustom = function( config ) {
    config.baseHref = '/';//设置基础路径，加载样式文件时有用
    config.language = 'zh-cn';//设置语言为中文

    config.toolbarGroups = [
        { name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
        { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
        { name: 'styles', groups: [ 'styles' ] },
        { name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
        { name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
        { name: 'links', groups: [ 'links' ] },
        { name: 'forms', groups: [ 'forms' ] },
        { name: 'insert', groups: [ 'insert' ] },
        '/',
        { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] },
        '/',
        { name: 'colors', groups: [ 'colors' ] },
        { name: 'tools', groups: [ 'tools' ] },
        { name: 'others', groups: [ 'others' ] },
        { name: 'about', groups: [ 'about' ] }
    ];

    config.removeButtons = 'Source,Save,NewPage,Preview,Print,Templates,Cut,Copy,Paste,PasteText,PasteFromWord,Find,Replace,SelectAll,Scayt,Form,Checkbox,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField,Strike,Subscript,Superscript,NumberedList,BulletedList,Indent,Outdent,Blockquote,CreateDiv,JustifyLeft,JustifyCenter,JustifyRight,JustifyBlock,BidiLtr,BidiRtl,Language,Anchor,Unlink,Flash,HorizontalRule,Smiley,SpecialChar,PageBreak,Iframe,Format,Font,FontSize,TextColor,BGColor,Maximize,ShowBlocks,About';
    config.removePlugins = 'easyimage,cloudservices,image';
    config.extraPlugins = 'codesnippet,image2,resize,confighelper';

    config.image_previewText = ' ';

    config.filebrowserBrowseUrl = '/upload';
    config.filebrowserUploadUrl = '/comment/uploadImage';

    config.codeSnippet_theme = 'vue';
    config.codeSnippet_languages = {
        apache: 'Apache',
        bash: 'Bash',
        coffeescript: 'CoffeeScript',
        cpp: 'C++',
        c: 'C',
        cs: 'C#',
        css: 'CSS',
        diff: 'Diff',
        glsl: 'GLSL',
        golang: 'Golang',
        html: 'HTML',
        http: 'HTTP',
        ini: 'INI',
        java: 'Java',
        javascript: 'JavaScript',
        json: 'JSON',
        makefile: 'Makefile',
        markdown: 'Markdown',
        nginx: 'Nginx',
        objectivec: 'Objective-C',
        perl: 'Perl',
        php: 'PHP',
        python: 'Python',
        ruby: 'Ruby',
        sql: 'SQL',
        shell: 'Shell Session',
        typescript: 'TypeScript',
        vbscript: 'VBScript',
        verilog: 'Verilog',
        xhtml: 'XHTML',
        xml: 'XML'
    };
};