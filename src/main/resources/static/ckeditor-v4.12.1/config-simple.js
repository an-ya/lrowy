/**
 * 简单版配置
 */

CKEDITOR.editorConfigCustom = function( config ) {
    config.baseHref = '/';//设置基础路径，加载样式文件时有用
    config.language = 'zh-cn';//设置语言为中文

    config.toolbarGroups = [
        { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
        { name: 'colors', groups: [ 'colors' ] },
        { name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
        { name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
        { name: 'forms', groups: [ 'forms' ] },
        { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] },
        '/',
        { name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
        { name: 'styles', groups: [ 'styles' ] },
        { name: 'links', groups: [ 'links' ] },
        { name: 'insert', groups: [ 'insert' ] },
        '/',
        { name: 'tools', groups: [ 'tools' ] },
        { name: 'others', groups: [ 'others' ] },
        { name: 'about', groups: [ 'about' ] }
    ];

    config.removeButtons = 'Format,Font,FontSize,NumberedList,Outdent,Blockquote,JustifyLeft,BidiLtr,Form,Scayt,SelectAll,Find,Cut,Source,Templates,Flash,HorizontalRule,Smiley,PageBreak,Iframe,Maximize,ShowBlocks,About,Save,Print,Preview,NewPage,Copy,Paste,PasteText,PasteFromWord,Replace,Checkbox,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField,BulletedList,Indent,CreateDiv,JustifyCenter,JustifyRight,JustifyBlock,BidiRtl,Language,Anchor';
    config.removePlugins = 'easyimage,cloudservices,image';
    config.extraPlugins = 'codesnippet,image2,mathjax,resize,confighelper';

    config.image_previewText = ' ';

    config.mathJaxClass = 'mjx';
    config.mathJaxLib = 'https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.5/latest.js?config=TeX-MML-AM_CHTML';

    config.filebrowserBrowseUrl = '/browser/browse.php';
    config.filebrowserUploadUrl = '/uploader/upload.php';

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