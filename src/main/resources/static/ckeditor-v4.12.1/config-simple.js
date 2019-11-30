/**
 * 简单版配置
 */

CKEDITOR.editorConfig = function( config ) {
    config.baseHref = '/';//设置基础路径，加载样式文件时有用
    config.language = 'zh-cn';//设置语言为中文

    config.toolbarGroups = [
        { name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
        { name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
        { name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
        { name: 'forms', groups: [ 'forms' ] },
        { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
        { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] },
        { name: 'links', groups: [ 'links' ] },
        '/',
        '/',
        { name: 'styles', groups: [ 'styles' ] },
        { name: 'colors', groups: [ 'colors' ] },
        { name: 'insert', groups: [ 'insert' ] },
        { name: 'tools', groups: [ 'tools' ] },
        { name: 'others', groups: [ 'others' ] },
        { name: 'about', groups: [ 'about' ] }
    ];

    config.removeButtons = 'Save,Templates,Source,NewPage,Preview,Print,Cut,Copy,Paste,PasteText,PasteFromWord,Replace,Find,Scayt,Form,Checkbox,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField,CreateDiv,BidiLtr,BidiRtl,Language,Anchor,Flash,HorizontalRule,PageBreak,Iframe,Maximize,ShowBlocks,About';

    config.removeButtons = 'save,Form,Checkbox,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField,About';
    config.removePlugins = 'easyimage,cloudservices,image';
    config.extraPlugins = 'codesnippet,image2,mathjax,resize';
    config.codeSnippet_theme = 'vue';

    config.mathJaxClass = 'mjx';
    config.mathJaxLib = 'https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.5/latest.js?config=TeX-MML-AM_CHTML';

    config.filebrowserBrowseUrl = '/browser/browse.php';
    config.filebrowserUploadUrl = '/uploader/upload.php';

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

    config.image_previewText = ' ';
    config.mathJaxLib = 'https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.5/MathJax.js?config=TeX-MML-AM_CHTML';//设置公式插件的库
};