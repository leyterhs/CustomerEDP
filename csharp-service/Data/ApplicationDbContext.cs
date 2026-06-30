using Microsoft.EntityFrameworkCore;
using csharp_service.Models;

namespace csharp_service.Data;

public class ApplicationDbContext : DbContext
{
    public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
        : base(options)
    {
    }

    public DbSet<Engagement> Engagements { get; set; }
    public DbSet<Delivery> Deliveries { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Engagement>()
            .ToTable("engagements")
            .HasKey(e => e.Id)
            .HasName("engagements_pkey");

        modelBuilder.Entity<Engagement>()
            .Property(e => e.Id)
            .HasColumnName("id");

        modelBuilder.Entity<Engagement>()
            .Property(e => e.Title)
            .HasColumnName("title");

        modelBuilder.Entity<Engagement>()
            .Property(e => e.Description)
            .HasColumnName("description");

        modelBuilder.Entity<Engagement>()
            .Property(e => e.ClientId)
            .HasColumnName("client_id");

        modelBuilder.Entity<Engagement>()
            .Property(e => e.Status)
            .HasColumnName("status");

        modelBuilder.Entity<Engagement>()
            .Property(e => e.Budget)
            .HasColumnName("budget");

        modelBuilder.Entity<Engagement>()
            .Property(e => e.Deadline)
            .HasColumnName("deadline");

        modelBuilder.Entity<Engagement>()
            .Property(e => e.CreatedBy)
            .HasColumnName("created_by");

        modelBuilder.Entity<Engagement>()
            .Property(e => e.CreatedAt)
            .HasColumnName("created_at");

        modelBuilder.Entity<Delivery>()
            .ToTable("deliveries")
            .HasKey(d => d.Id)
            .HasName("deliveries_pkey");

        modelBuilder.Entity<Delivery>()
            .Property(d => d.Id)
            .HasColumnName("id");

        modelBuilder.Entity<Delivery>()
            .Property(d => d.Title)
            .HasColumnName("title");

        modelBuilder.Entity<Delivery>()
            .Property(d => d.Description)
            .HasColumnName("description");

        modelBuilder.Entity<Delivery>()
            .Property(d => d.EngagementId)
            .HasColumnName("engagement_id");

        modelBuilder.Entity<Delivery>()
            .Property(d => d.AssignedTo)
            .HasColumnName("assigned_to");

        modelBuilder.Entity<Delivery>()
            .Property(d => d.Priority)
            .HasColumnName("priority");

        modelBuilder.Entity<Delivery>()
            .Property(d => d.Status)
            .HasColumnName("status");

        modelBuilder.Entity<Delivery>()
            .Property(d => d.DueDate)
            .HasColumnName("due_date");

        modelBuilder.Entity<Delivery>()
            .Property(d => d.CreatedAt)
            .HasColumnName("created_at");
    }
}