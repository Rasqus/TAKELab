using WebApiAjax;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();

var app = builder.Build();

app.MapGet("/api/math", (int x, int y) => new CalcResult()
{
    Sum = x + y,
    Difference = x - y,
    Product = x * y,
    Quotient = x / y
});

app.UseStaticFiles();

app.Run();