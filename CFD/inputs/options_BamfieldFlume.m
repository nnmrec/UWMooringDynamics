%% set the initial conditions for the "Mezzanine"
OPTIONS = [];
starSimFile = 'BamfieldFlume_v0.sim';
runOnHPC    = false;
nCPUs       = 4;

% coordinates at points along the mooring lines, and the buoyancy pods (plus anything else not a turbine)
% NOTE: avoid using colons, :, in these names (or else!)
probes.names = {'probe-01'; ...
                'probe-02'; ...
                'probe-03'};            
 probes.xyz =  [4.00 0.5 0.4; ...
                6.25 0.5 0.4; ...
                8.50 0.5 0.4];

% coordinates of turbines (center of rotor), and other properties
rotors.names  = {'turbine-upstream'; ...
                 'turbine-middle'; ...
                 'turbine-downstream'};
rotors.tables = {'UW-LabScale-2_tsr7p0'; ...
                 'UW-LabScale-2_tsr7p0'; ...
                 'UW-LabScale-2_tsr7p0'};
% rotor_rpm,x,y,z,nx,ny,nz,rotor_radius,hub_radius,rotor_thick
rotors.data = [300.0,	4.00,  0.44375,    0.4,	1,	0,	0,	0.225,	0,	0.05; ...
               300.0,	6.25,  0.50000,    0.4,	1,	0,	0,	0.225,	0,	0.05; ...
               300.0,	8.50,  0.55625,    0.4,	1,	0,	0,	0.225,	0,	0.05];





// idea ... could make these inputs also in Matlab format, and then Java reads the matlab files
// sp that both codes can have access to these variables ;)
// the java will have to be fixed format unless we can search by name
// ... in java, read entire file into a single list, then search for the variable
// name in the file to make assignments. cons: changing a variable name in matlab must also change in java
// when have choice of options file ... how to tell STAR which to choose??
//  you can hardcode the filename in the macro ... or read the files name from a matlab file

// options.physics
///////////////////////////////////////////////////////////////////////////////
// USER INPUTS
//
static final double density             = 1025;     // fluid density [kg/m^2]
static final double dynamic_viscosity   = 0.00108;  // fluid dynamic viscosity [Pa-s]
static final double init_TI             = 0.1;      // turbulence intensity, TI = u' / U [unitless]
static final double init_Lturb          = 3.125;    // turbulent length scale [m]
static final double init_Vturb          = 0.1;      // turbulent velocity scale [m/s]
static final double init_Vx             = 0.0;      // initial x-velocity [m/s]
static final double init_Vy             = 0.0;      // initial y-velocity [m/s]
static final double init_Vz             = 0.0;      // initial z-velocity [m/s]
///////////////////////////////////////////////////////////////////////////////

// options.boundaries
///////////////////////////////////////////////////////////////////////////////
// USER INPUTS
//
static final double length              = 562.5;    // length in x-dimention (steamwise) [m]
static final double width               = 500;      // length in y-dimention (crossflow) [m]
static final double depth               = 60;       // length in z-dimention (vertical) [m]
static final double bc_TI               = 0.1;      // turbulence intensity for inlet and outlet TI = u' / U [unitless]
static final double bc_Lturb            = 3.125;    // turbulent length scale for inlet and outlet [m]
static final double inlet_Vx            = 1.0;      // inlet x-velocity [m/s]
static final double inlet_Vy            = 0.0;      // inlet y-velocity [m/s]
static final double inlet_Vz            = 0.0;      // inlet z-velocity [m/s]
static final double z0                  = 0.01;     // seabed surface roughness height [m]
///////////////////////////////////////////////////////////////////////////////

// options.meshing
///////////////////////////////////////////////////////////////////////////////
// USER INPUTS
//
static final double diameter_rotor          = 25;         // rotor diameter [m]
static final double depth                   = 60;     // length in z-dimention (vertical) [m]  
static final double core_baseSize           = 25.0;   // base size of core mesh [m]
static final double core_sizeSurface        = 5.55;   // surface size of core mesh [m]
static final double core_maxSize            = 30.0;   // max size of cells in the core mes
// static final double core_growthSurface      = '';     // growth rate from surfaces
// static final double core_growthBase         = '';     // growth rate within core meshh
static final int    prism_numLayers         = 10;     // number prism layers
static final double prism_wallThickness     = 0.02;   // thickness of the first prism cell on the wall [m]
static final double prism_thickness         = 3.33;   // thickness of entire prism layer [m]
static final double prism_thicknessNeighbor = 2.0;    // multiplier of core mesh size to last prism layer [ratio of 2 to Inf]
static final double custom_seabedSize       = 6.66;   // target cell size on seabed
// static final double custom_seabedGrowth     = '';     // growth rate from seabed surface
static final int    iter_max                = 20;     // stop after this many iterations
static final double limit_continuity        = 1e-4;   // stop when residual of continuity reaches this value
///////////////////////////////////////////////////////////////////////////////

// options.solver
///////////////////////////////////////////////////////////////////////////////
// USER INPUTS
//
// String path0                = "Mezzanine_v0.sim";
static final int save_iters = 100;     // number of iterations to trigger the auto-save
///////////////////////////////////////////////////////////////////////////////
  