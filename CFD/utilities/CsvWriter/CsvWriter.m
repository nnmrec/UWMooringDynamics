classdef CsvWriter < handle
    % CSVWRITER writes numeric, cell and char matrices to csv file.
    %
    % Syntax:
    %    f = CsvWriter(file_name,optional_settings,...);
    %
    % Description:
    %    CSVWRITER writes matrices and arrays of numbers, cells and chars
    %    to a csv file. As an optional setting the delimiter, end-of-line
    %    characters, text quantifier and false/true representation can be
    %    set. Optional inputs are accepted in standard MATLAB format.
    %
    %    CSVWRITER first open the file with optional settings. Then any
    %    2D numeric, char and cell data can be appended. Data are only 
    %    appended, already written data cannot be modified unless the file 
    %    is rewritten from the begining. File can be closed manually or is 
    %    closed automatically by destructor.
    %
    %    Possible additional settings are:
    %       'delimiter'         ';'    (default)
    %                           ','
    %                           '|'
    %                           '\t'        horizontal tab
    %       'endofline'         '\n'   (default)
    %                           '\r'
    %                           '\r\n'
    %       'textqualifier'     ''     (none as default)
    %                           ''''        single quotation mark
    %                           '"'         double quotation mark
    %       'falsetrue'         {'FALSE','TRUE'} (default)
    %                           falsetrue is used for representation of
    %                           logical data-class. Another two-cell
    %                           combinations can be used. See examples.
    % Examples:
    %    f = CsvWriter('./test1.csv');
    %    f.append([1 2; 3 4]);
    %    f.close();
    %
    %    f = CsvWriter('./test2.csv','delimiter',',');
    %    f.append([1 2; 3 4]);
    %    f.close();
    %
    %    f = CsvWriter('./test3.csv','delimiter',',' ,'endofline','\n');
    %    f.append({'a', 1; true, 1E6});
    %    f.close();
    
    %    f = CsvWriter('./test4.csv','delimiter','|' ,'endofline','\r',  'textqualifier','''');
    %    f.append(['a' 'b'; 'c' 'd']);
    %    f.append({'a', 1; true, 1E6});
    %    f.close();
    %
    %    f = CsvWriter('./test5.csv','delimiter','\t','endofline','\r\n','textqualifier','"','falsetrue',{'F','T'});
    %    f.append([1 2; 3 4]);
    %    f.append(['a' 'b'; 'c' 'd']);
    %    f.append({'a', 1; true, false});
    %    f.append(''); % empty line
    %    f.append({'b', 2; false, false});
    %    f.close();
    %
    % Bugs and suggestions:
    %    Please send to Petr Dvorak, petrdvor at gmail dot com
    %
    % License:
    %    Code is available under the BSD license.
    %
    % Programmed and Copyright by Petr Dvorak: petrdvor at gmail dot com
    %    Version: 1.0 
    %    Date: 2015/07/24 
    
    properties(Constant)
        DEFAULT_DELIMITER       = ';'
        DEFAULT_EOL             = '\n'             % end of line
        DEFAULT_TEXT_QUALIFIER  = ''               % wrap string
        DEFAULT_FALSETRUE       = {'FALSE','TRUE'} % how are logical data printed
        
        % alowed optional inputs
        EXPECTED_DELIMITERS     = { ','  , ';'  , '|' , '\t' }
        EXPECTED_EOL            = { '\n' , '\r' , '\r\n'     }
        EXPECTED_TEXT_QUALIFIER = { '''' , '"'               }
    end
    
    properties(SetAccess=protected)
        fileId
        
        delimiter
        endofline
        textqualifier
        falsetrue
    end
    
    methods
        function [this] = CsvWriter(file,varargin) % Constructor
            
            this.fileId = fopen(file,'w');
            if this.fileId <= 2
                error(['Can''t open or create file: ', file]);
            end
            
            % parse optional inputs
            p = inputParser;
            addOptional(p,'delimiter',    this.DEFAULT_DELIMITER,      @(x)any(validatestring(x,this.EXPECTED_DELIMITERS)));
            addOptional(p,'endofline',    this.DEFAULT_EOL,            @(x)any(validatestring(x,this.EXPECTED_EOL)));
            addOptional(p,'textqualifier',this.DEFAULT_TEXT_QUALIFIER, @(x)any(validatestring(x,this.EXPECTED_TEXT_QUALIFIER)));
            addOptional(p,'falsetrue',    this.DEFAULT_FALSETRUE,      @(x)all([iscell(x),all(size(x)==[1 2]),ischar(x{1,1}),ischar(x{1,2})])); % must be two cells with strings, first false
            parse(p,varargin{:});
            
            this.delimiter     = p.Results.delimiter;
            this.endofline     = p.Results.endofline;
            this.textqualifier = p.Results.textqualifier;
            this.falsetrue     = p.Results.falsetrue;
        end % constructor
        
        function delete(this) % Destructor
            if this.fileId>2
                fclose(this.fileId);
            else
                error(['Can''t close file: ', this.fileId]);
            end
        end
        
        function [this] = close(this)
            this.delete;
        end
        
        function [this] = append(this,data)
            switch class(data)
                case 'cell'
                    for iRow=1:size(data,1)
                        strRow = [];
                        for iCol=1:size(data,2)
                            strRow = strcat(strRow, this.printElement(data{iRow,iCol}));
                        end
                        this.writeLine(strRow);
                    end
                case {'single','double','logical'}
                    for iRow=1:size(data,1)
                        strRow = [];
                        for iCol=1:size(data,2)
                            strRow = strcat(strRow, this.printElement(data(iRow,iCol)));
                        end
                        this.writeLine(strRow);
                    end
                case 'char'
                    if isempty(data)
                        % when data is an empty char make a empty line
                        strRow = printElement(this,data);
                        this.writeLine(strRow);
                    else
                        for iRow=1:size(data,1)
                            strRow = this.printElement(data(iRow,:));
                            this.writeLine(strRow);
                        end
                    end
                otherwise
                    errror('Uknown variable type to write to csv.')
            end
        end
        
        function str = printElement(this,element)
            switch class(element)
                case 'cell'
                    error('Unable to print cell from a cell');
                case 'char'
                    if ~isempty(element)
                        str = sprintf('%s%s%s',this.textqualifier,element,this.textqualifier);
                    else
                        str=''; % to print empty line without a text qualifier
                    end
                case 'double'
                    str = sprintf('%g',element);
                case 'single'
                    str = sprintf('%g',element);
                case 'logical'
                    str = sprintf('%s',this.falsetrue{element+1});
            end
            str = strcat(str,this.delimiter);
        end
        
        function writeLine(this,stringLine)
            fprintf(this.fileId,strcat(stringLine(1:end-length(this.delimiter)),this.endofline));
        end
    end % methods
end % classdef